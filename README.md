# Insurance_Android_App

ffmpeg -i "HW5_99105764.mov" -r 25 -vf "scale=-2:720" -c:v libx265 -crf 28 -c:a libopus -b:a 32K -strict experimental -max_muxing_queue_size 1024 "HW5_99105764.mp4"

ffmpeg -i HW5_99105764_reduced.mov -c:v libx264 -crf 18 -preset veryslow -c:a copy HW5_99105764_reduced_reduced.mov