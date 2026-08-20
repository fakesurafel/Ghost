# Ghost Gram implementation specification

Ghost Gram is a rebranded Android client based on the existing Telegram source tree.

## Product surface

The visible product should focus on private one-to-one conversations with human users. The chat list must exclude groups, supergroups, channels, bots, stories, community views, Wallet, TON, and Stars entry points. The app may retain profile and security settings needed to manage the account and Ghost Mode.

## Branding

The display name is `Ghost Gram`. The package identifier is `com.ghostgram.app` for the main build, with existing build-type suffixes preserved. Launcher assets use a new ghost-plus-chat-bubble symbol and must not use Telegram's paper-plane logo.

## Ghost Mode

Ghost Mode is an explicit user setting stored locally. When enabled, the client must avoid sending read-history acknowledgements to the server while still updating local unread state so the user can read messages without the normal receipt being sent. The UI must explain that this only controls read-history acknowledgements and is not a promise that every network signal is hidden.

## Authentication

The existing normal phone-number login remains the primary path. A new login-method chooser should expose normal phone login, session-string import, and API-credential login. Session strings must not be fabricated or logged. The code should distinguish supported formats and refuse invalid or unsupported strings rather than claiming that a placeholder value is a real authenticated session. API credential input must be handled as sensitive data, kept out of logs, and stored only if the existing client architecture can safely use it.

## Session profile

After a successful login, the profile should expose a security section where the user can view and copy the current session export only when a real export is available. The UI must never display fabricated auth keys. Clipboard copying should use a sensitive clip label and should avoid persistent logging.

## Scope note

The initial implementation should enforce the requested behavior at central list/navigation and read-acknowledgement boundaries. It should not delete Telegram protocol code that is required for compatibility with the server; it should remove or hide those capabilities from the Ghost Gram user experience.
