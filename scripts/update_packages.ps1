# PowerShell script to update package structure
$baseDir = "$PSScriptRoot\..\app\src\main\java"
$targetBase = "$baseDir\dev\aurakai\auraframefx"

# Create target directory if it doesn't exist
if (-not (Test-Path -Path $targetBase))
{
    New-Item -ItemType Directory -Path $targetBase -Force
}

# Function to update package declarations in a file
function Update-PackageDeclaration
{
    param (
        [string]$filePath
    )

    $content = Get-Content -Path $filePath -Raw -Encoding UTF8
    $updated = $false

    # Update package declarations
    if ($content -match '^\s*package\s+(com\.example|com\.aegenesis|auraframefx|com\.auraframefx)(\..*)?\s*$')
    {
        $content = $content -replace '^\s*package\s+(com\.example|com\.aegenesis|auraframefx|com\.auraframefx)(\..*)?', 'package dev.aurakai.auraframefx$2'
        $updated = $true
    }

    # Update imports
    $content = $content -replace 'import\s+(com\.example|com\.aegenesis|auraframefx|com\.auraframefx)\.', 'import dev.aurakai.auraframefx.'

    if ($updated)
    {
        $content | Set-Content -Path $filePath -Encoding UTF8 -NoNewline
        Write-Host "Updated package declarations in $filePath"
    }
}

# Process files in auraframefx directory
$sourceDirs = @(
    "$baseDir\auraframefx"
    "$baseDir\com\auraframefx"
)

foreach ($dir in $sourceDirs)
{
    if (Test-Path -Path $dir)
    {
        Get-ChildItem -Path $dir -Recurse -File | ForEach-Object {
            $relativePath = $_.FullName.Substring($dir.Length).TrimStart('\\', '/')
            $targetPath = Join-Path -Path $targetBase -ChildPath $relativePath
            $targetDir = [System.IO.Path]::GetDirectoryName($targetPath)

            if (-not (Test-Path -Path $targetDir))
            {
                New-Item -ItemType Directory -Path $targetDir -Force
            }

            # Copy the file
            Copy-Item -Path $_.FullName -Destination $targetPath -Force

            # Update package declarations in the copied file
            Update-PackageDeclaration -filePath $targetPath
        }
    }
}

# Also update any remaining files in the target directory that might have old package references
Get-ChildItem -Path $targetBase -Recurse -Include *.kt, *.java | ForEach-Object {
    Update-PackageDeclaration -filePath $_.FullName
}

Write-Host "Package structure update complete. Files have been moved to $targetBase"
