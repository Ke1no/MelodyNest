"""Regenerate the original 4-second mono WAV used for local playback tests."""
from pathlib import Path
import math, struct, wave
target=Path(__file__).resolve().parents[1]/'app/src/main/res/raw/demo_tone.wav'
target.parent.mkdir(parents=True,exist_ok=True)
rate=16000
with wave.open(str(target),'wb') as output:
    output.setnchannels(1);output.setsampwidth(2);output.setframerate(rate)
    for i in range(4*rate):
        t=i/rate;local=t%1;frequency=[261.63,329.63,392.0,523.25][int(t)]
        envelope=min(local/.05,1,(1-local)/.15)
        value=int(0.18*32767*max(0,envelope)*math.sin(2*math.pi*frequency*t))
        output.writeframesraw(struct.pack('<h',value))
print(target)
