# WorkmanIME

Simple Android app that remaps a physical keyboard to [Workman Layout](https://workmanlayout.org).

## How does it do this?

Implements an [InputMethodService](https://developer.android.com/reference/android/inputmethodservice/InputMethodService)
that modifies incoming [KeyEvent](https://developer.android.com/reference/android/view/KeyEvent)s.

## Workman flavour

The only flavour supported by the app is Workman-P.
![Visualisation of layout](https://github.com/kdeloach/workman/raw/gh-pages/images/workman_p.png)
Feel free to submit a PR if you like to add switching behaviour to support other flavours like
Workman (original), Workman-IO, etc.

## Build & install

Easiest is to use Android studio.

1. Open project in Android studio
2. Connect your Android device with a USB cable (ensure USB debugging is enabled in developer options)
3. Select the device in Android studio, and hit "Run"

It will build the app and install it on your device.

## How to enable the input method?

You need to enable the input method. This setting can be found somewhere in
Settings > System > Languages & Input, depending on the device.
Then, you need to set it as the default (Choose input method).

## Notes

Pretty much everything is hardcoded. This is a tool for a very simple specific job.
Feel free to fork this or take inspiration from the code in the `WorkmanIME` class to implement your preferred keyboard layout.