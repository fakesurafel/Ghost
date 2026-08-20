from pathlib import Path
from PIL import Image, ImageOps

ROOT = Path(__file__).resolve().parents[1]
SOURCE = ROOT / "assets" / "ghostgram_logo.png"
RES = ROOT / "TMessagesProj" / "src" / "main" / "res"
DENSITY_SIZES = {
    "mipmap-mdpi": 48,
    "mipmap-hdpi": 72,
    "mipmap-xhdpi": 96,
    "mipmap-xxhdpi": 144,
    "mipmap-xxxhdpi": 192,
}

source = Image.open(SOURCE).convert("RGBA")
for density, size in DENSITY_SIZES.items():
    for path in sorted((RES / density).glob("*.png")):
        if "launcher" not in path.name and path.name not in {"ic_launcher.png", "ic_launcher_round.png"}:
            continue
        icon = ImageOps.fit(source, (size, size), method=Image.Resampling.LANCZOS, centering=(0.5, 0.5))
        icon.save(path, format="PNG", optimize=True)
        print(path)
