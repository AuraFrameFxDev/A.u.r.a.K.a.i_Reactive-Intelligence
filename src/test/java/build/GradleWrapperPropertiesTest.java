package build;

/*
 Testing library/framework: JUnit 5 (JUnit Jupiter).
 If your project uses JUnit 4, replace:
   - org.junit.jupiter.api.Test -> org.junit.Test
   - org.junit.jupiter.api.Assertions.* -> org.junit.Assert.*
   - @DisplayName can be removed or replaced with comments
*/

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GradleWrapperPropertiesTest {

    private static final Path PROPS_PATH = Paths.get("gradle/wrapper/gradle-wrapper.properties");

    private static String readAll() throws IOException {
        assertTrue(Files.exists(PROPS_PATH), "gradle/wrapper/gradle-wrapper.properties must exist");
        return Files.readString(PROPS_PATH);
    }

    private static Properties loadProps() throws IOException {
        assertTrue(Files.exists(PROPS_PATH), "gradle/wrapper/gradle-wrapper.properties must exist");
        Properties p = new Properties();
        try (InputStream in = Files.newInputStream(PROPS_PATH)) {
            p.load(in);
        }
        return p;
    }

    @Test
    @DisplayName("File exists at the expected path")
    void fileExists() {
        assertTrue(Files.exists(PROPS_PATH), "gradle/wrapper/gradle-wrapper.properties must exist");
    }

    @Test
    @DisplayName("No unresolved Git merge conflict markers present")
    void noMergeConflictMarkers() throws IOException {
        String content = readAll();
        assertFalse(content.contains("<<<<<<<"), "Found '<<<<<<<' merge marker in gradle-wrapper.properties");
        assertFalse(content.contains("======="), "Found '=======' merge marker in gradle-wrapper.properties");
        assertFalse(content.contains(">>>>>>>"), "Found '>>>>>>>' merge marker in gradle-wrapper.properties");
    }

    @Test
    @DisplayName("Required wrapper properties are present")
    void requiredKeysPresent() throws IOException {
        Properties p = loadProps();
        List<String> required = Arrays.asList(
                "distributionBase",
                "distributionPath",
                "distributionUrl",
                "zipStoreBase",
                "zipStorePath"
        );
        for (String key : required) {
            assertNotNull(p.getProperty(key), "Missing required property: " + key);
        }
    }

    @Test
    @DisplayName("Duplicate keys, if any, must all have identical values")
    void duplicateKeysConsistent() throws IOException {
        List<String> lines = Files.readAllLines(PROPS_PATH);
        Map<String, Set<String>> valuesByKey = new LinkedHashMap<>();

        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;

            int idxEq = line.indexOf('=');
            int idxCol = line.indexOf(':');
            int idx = (idxEq >= 0 && (idxCol < 0 || idxEq < idxCol)) ? idxEq : idxCol;
            if (idx < 0) continue; // not a key-value entry

            String key = line.substring(0, idx).trim();
            String value = line.substring(idx + 1).trim();

            valuesByKey.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(value);
        }

        List<String> conflicting = new ArrayList<>();
        for (Map.Entry<String, Set<String>> e : valuesByKey.entrySet()) {
            if (e.getValue().size() > 1) {
                conflicting.add(e.getKey() + " -> " + e.getValue());
            }
        }

        assertTrue(conflicting.isEmpty(),
                "Duplicate keys with differing values found in gradle-wrapper.properties:\n" + String.join("\n", conflicting));
    }

    @Test
    @DisplayName("distributionUrl is HTTPS, from services.gradle.org, ends with .zip, and references bin/all")
    void distributionUrlSecureAndWellFormed() throws IOException {
        Properties p = loadProps();
        String url = p.getProperty("distributionUrl");
        assertNotNull(url, "distributionUrl must be present");

        assertTrue(url.startsWith("https://services.gradle.org/distributions/"),
                "distributionUrl must start with https://services.gradle.org/distributions/");
        assertTrue(url.endsWith(".zip"), "distributionUrl must end with .zip");
        assertTrue(url.contains("-bin.zip") || url.contains("-all.zip"),
                "distributionUrl should reference either the bin or all distribution");
    }

    @Test
    @DisplayName("Boolean Gradle flags are valid 'true' or 'false' values")
    void booleanFlagsValid() throws IOException {
        Properties p = loadProps();
        String[] keys = new String[]{
                "org.gradle.configuration-cache",
                "org.gradle.unsafe.configuration-cache",
                "org.gradle.caching",
                "org.gradle.vfs.watch",
                "org.gradle.parallel",
                "org.gradle.java.installations.auto-download",
                "validateDistributionUrl"
        };

        for (String k : keys) {
            String v = p.getProperty(k);
            assertNotNull(v, "Missing boolean property: " + k);
            assertTrue("true".equalsIgnoreCase(v) || "false".equalsIgnoreCase(v),
                    "Property " + k + " must be 'true' or 'false' but was: " + v);
        }
    }

    @Test
    @DisplayName("networkTimeout is a positive integer (milliseconds) with a reasonable upper bound")
    void networkTimeoutIsPositiveInteger() throws IOException {
        Properties p = loadProps();
        String v = p.getProperty("networkTimeout");
        assertNotNull(v, "networkTimeout must be present");
        int val;
        try {
            val = Integer.parseInt(v);
        } catch (NumberFormatException e) {
            fail("networkTimeout must be an integer number of milliseconds, but was: " + v);
            return;
        }
        assertTrue(val > 0, "networkTimeout must be positive");
        assertTrue(val <= 600_000, "networkTimeout seems excessively large (> 10 minutes): " + val);
    }

    @Test
    @DisplayName("org.gradle.jvmargs includes explicit UTF-8 file.encoding")
    void jvmArgsContainEncoding() throws IOException {
        Properties p = loadProps();
        String jvm = p.getProperty("org.gradle.jvmargs");
        assertNotNull(jvm, "org.gradle.jvmargs must be present");
        assertTrue(jvm.contains("-Dfile.encoding=UTF-8"),
                "org.gradle.jvmargs should include -Dfile.encoding=UTF-8");
    }

    @Test
    @DisplayName("zipStoreBase and zipStorePath are set to expected values")
    void zipStorePathsValid() throws IOException {
        Properties p = loadProps();
        String base = p.getProperty("zipStoreBase");
        String path = p.getProperty("zipStorePath");
        assertNotNull(base, "zipStoreBase must be present");
        assertNotNull(path, "zipStorePath must be present");
        assertEquals("GRADLE_USER_HOME", base, "zipStoreBase should be GRADLE_USER_HOME");
        assertEquals("wrapper/dists", path, "zipStorePath should be wrapper/dists");
    }
}