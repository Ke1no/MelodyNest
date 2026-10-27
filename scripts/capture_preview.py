"""Capture the running demo on a clean emulator for the repository README."""
from pathlib import Path
import subprocess
import time

def adb(*args):
    return subprocess.check_output(['adb', *args])

output = Path('preview-output')
output.mkdir(exist_ok=True)
adb('install', '-r', 'app/build/outputs/apk/debug/app-debug.apk')
adb('shell', 'am', 'start', '-W', '-n', 'com.keino.minispotify/.MainActivity')
for _ in range(20):
    adb('shell', 'uiautomator', 'dump', '/sdcard/window.xml')
    xml = adb('shell', 'cat', '/sdcard/window.xml').decode()
    if 'Made for your break' in xml and 'Favorites' in xml:
        break
    time.sleep(1)
else:
    raise RuntimeError('The demo feed did not become visible')
(output / 'feed.png').write_bytes(adb('exec-out', 'screencap', '-p'))
(output / 'ui.xml').write_text(xml)
print('Captured the running Compose feed on an API 35 emulator.')
