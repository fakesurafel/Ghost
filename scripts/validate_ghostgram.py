from pathlib import Path
import xml.etree.ElementTree as ET

root = Path(__file__).resolve().parents[1]
strings = root / "TMessagesProj/src/main/res/values/strings.xml"
ET.parse(strings)
text = strings.read_text()
required_strings = ["Ghost Gram", "Ghost Mode", "Session key ID", "Login method"]
for value in required_strings:
    assert value in text, f"missing resource text: {value}"

required_files = [
    root / "TMessagesProj/src/main/java/org/telegram/ui/GhostGramLoginActivity.java",
    root / "TMessagesProj/src/main/java/org/telegram/messenger/GhostReadManager.java",
    root / "TMessagesProj/src/main/java/org/telegram/messenger/PrivateChatFilter.java",
    root / "TMessagesProj/src/main/java/org/telegram/messenger/SessionExportHelper.java",
    root / "TMessagesProj/src/main/res/drawable/ghostgram_logo.png",
]
for path in required_files:
    assert path.exists(), f"missing required file: {path}"

messages = (root / "TMessagesProj/src/main/java/org/telegram/messenger/MessagesController.java").read_text()
assert "PrivateChatFilter.isPrivateMode()" in messages
assert "GhostReadManager.getInstance().shouldBlockReadReceipt()" in messages

session = (root / "TMessagesProj/src/main/java/org/telegram/messenger/SessionExportHelper.java").read_text()
assert "return null;" in session
assert "new byte[256]" not in session

print("Ghost Gram static validation passed")
