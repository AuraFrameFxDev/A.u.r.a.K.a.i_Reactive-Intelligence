# PowerShell script to fix package structure
# This will move files from incorrect package directories to dev/aurakai/auraframefx

$baseDir = "$PSScriptRoot\..\app\src\main\java"
$targetDir = "$baseDir\dev\aurakai\auraframefx"

# Create target directory if it doesn't exist
if (-not (Test-Path -Path $targetDir))
{
    New-Item -ItemType Directory -Path $targetDir -Force
}

# Move files from auraframefx directory
$sourceDirs = @(
    "$baseDir\auraframefx",
    "$baseDir\com\auraframefx"
)

foreach ($sourceDir in $sourceDirs)
{
    if (Test-Path -Path $sourceDir)
    {
        Write-Host "Moving files from $sourceDir to $targetDir"
        Get-ChildItem -Path $sourceDir -Recurse -File | ForEach-Object {
            $relativePath = $_.FullName.Substring($sourceDir.Length + 1)
            $targetPath = Join-Path -Path $targetDir -ChildPath $relativePath
            $targetParent = [System.IO.Path]::GetDirectoryName($targetPath)

            if (-not (Test-Path -Path $targetParent))
            {
                New-Item -ItemType Directory -Path $targetParent -Force
            }

            Move-Item -Path $_.FullName -Destination $targetPath -Force

            # Update package declaration in the file
            $content = Get-Content -Path $targetPath -Raw -Encoding UTF8
            $content = $content -replace 'package (com\.example|com\.aegenesis|auraframefx|com\.auraframefx)', 'package dev.aurakai.auraframefx'
            $content | Set-Content -Path $targetPath -Encoding UTF8 -NoNewline
        }

        # Remove empty directories
        Get-ChildItem -Path $sourceDir -Recurse -Directory |
                Where-Object { -not (Get-ChildItem -Path $_.FullName -Recurse -File) } |
                Sort-Object -Property FullName -Descending |
                Remove-Item -Force -Recurse
    }
}

# Update package declarations in all Kotlin and Java files
Get-ChildItem -Path $targetDir -Recurse -Include *.kt, *.java | ForEach-Object {
    $content = Get-Content -Path $_.FullName -Raw -Encoding UTF8
    $content = $content -replace 'package (com\.example|com\.aegenesis|auraframefx|com\.auraframefx)', 'package dev.aurakai.auraframefx'
    $content | Set-Content -Path $_.FullName -Encoding UTF8 -NoNewline
}

Write-Host "Package structure has been fixed. All files are now under $targetDir"
