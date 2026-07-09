# Balance Tracker — Android Home Screen Widget

A minimal Android widget that shows a running balance. Debits (payments) subtract,
credits (income) add. All values persist in SharedPreferences.

## How it works
- **`BalanceStore.kt`** — single source of truth for the balance, saved to SharedPreferences.
- **`BalanceWidgetProvider.kt`** — renders the home-screen widget and refreshes it
  whenever a transaction is added.
- **`MainActivity.kt`** — a small screen (opened by tapping the widget) with two
  buttons, **+ Credit** and **− Debit**, and an amount field.

## How to build
1. Open this folder (`BalanceTracker/`) in **Android Studio** (Hedgehog or newer).
2. Let Gradle sync — it will pull the AndroidX/Material dependencies automatically.
3. Run on a device or emulator (`Run ▶`).
4. Long-press your home screen → Widgets → find **Balance Tracker** → drag it onto
   the home screen.
5. Tap the widget to open the app and add a credit or debit — the widget updates
   immediately.

## Customizing
- **Currency**: change `Locale("en", "IN")` in `MainActivity.kt` and
  `BalanceWidgetProvider.kt` to your locale (e.g. `Locale.US` for $).
- **Auto-refresh interval**: `updatePeriodMillis` in `res/xml/widget_info.xml`
  (currently 30 min; Android enforces a 30-min minimum for passive updates —
  the widget also refreshes instantly on every transaction regardless).
- **Add a transaction history**: currently only the running total is stored.
  To log each transaction, extend `BalanceStore` to save a list (e.g. as JSON)
  instead of just the float.

## Notes
- Minimum SDK: 23 (Android 6.0+)
- No internet permission needed — everything is stored locally on-device.

## Getting an installable APK (no Android Studio needed)

This project includes a GitHub Actions workflow (`.github/workflows/build-apk.yml`)
that builds the APK for you automatically. Steps:

1. Create a free GitHub account if you don't have one, and create a new
   **public or private repository** (e.g. `balance-tracker`).
2. Upload/push everything in this `BalanceTracker` folder to that repository
   (on github.com you can just drag-and-drop the unzipped folder contents
   using "Add file → Upload files", or use `git push` if you're comfortable
   with git).
3. Go to the **Actions** tab of your repository. A workflow run should start
   automatically (or click "Run workflow" if it doesn't).
4. Wait ~2–3 minutes for it to finish (green checkmark).
5. Click into the finished run → under **Artifacts**, download
   **BalanceTracker-debug-apk** — this is a zip containing `app-debug.apk`.
6. Transfer `app-debug.apk` to your Android phone (email it to yourself,
   Google Drive, USB, etc.) and tap it to install. You'll need to allow
   "install unknown apps" for whichever app you used to open it (Android
   will prompt you automatically the first time).

### Alternative: build with Android Studio and install directly
If you'd rather not use GitHub:
1. Install Android Studio (free, from developer.android.com/studio).
2. Open this folder as a project, let Gradle sync.
3. Connect your Android phone via USB with **USB debugging** enabled
   (Settings → About phone → tap "Build number" 7 times → Developer options →
   enable USB debugging), or use an emulator.
4. Click **Run ▶** — Android Studio installs the app directly onto your
   connected phone.
5. Alternatively, use **Build → Build App Bundle(s) / APK(s) → Build APK(s)**
   to generate the same `app-debug.apk` without connecting a device, then
   transfer it to your phone as described above.
