# PowerShell script to update package references in build files
$baseDir = "$PSScriptRoot\.."

# Function to update package references in a file
function Update-BuildFile
{
    param (
        [string]$filePath
    )

    $content = Get-Content -Path $filePath -Raw -Encoding UTF8
    $updated = $false

    # Update package references
    if ($content -match '(com\.example|com\.aegenesis|auraframefx|com\.auraframefx)')
    {
        $content = $content -replace '(com\.example|com\.aegenesis|auraframefx|com\.auraframefx)', 'dev.aurakai.auraframefx'
        $updated = $true
    }

    if ($updated)
    {
        $content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline
        Write-Host "Updated package references in $filePath"
    }
}

# Process build files
$buildFiles = Get-ChildItem -Path $baseDir -Recurse -Include "*.gradle.kts", "*.gradle", "*.xml", "*.properties" -Exclude "build", ".gradle", "*.iml"

foreach ($file in $buildFiles)
{
    Update-BuildFile -filePath $file.FullName
}

Write-Host "Build files update complete"
