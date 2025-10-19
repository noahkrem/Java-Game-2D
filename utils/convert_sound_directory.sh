for f in ../res/sound/*.wav; do
    ffmpeg -y -i "$f" -acodec pcm_s16le -ar 44100 -ac 2 "${f%.wav}-fixed.wav"
done