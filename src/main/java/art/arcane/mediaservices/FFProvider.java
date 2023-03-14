package art.arcane.mediaservices;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FFProvider {
    // Builds from https://github.com/eugeneware/ffmpeg-static#sources-of-the-binaries

    public static FFmpeg ffmpeg() throws IOException {
        return new FFmpeg(installFFmpeg());
    }

    public static FFprobe ffprobe() throws IOException {
        return new FFprobe(installFFprobe());
    }

    public static File getInstallFolder() {
        return new File("ffportable");
    }

    public static void extract(String path, File file) throws IOException {
        System.out.println("Installing " + path + " to " + file.getAbsolutePath());
        file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        FFProvider.class.getResourceAsStream(path).transferTo(fos);
        fos.close();
    }

    public static String installFFmpeg() throws IOException {
        File file = new File(getInstallFolder(), "ffmpeg");

        if(file.exists())
        {
            return file.getAbsolutePath();
        }

        else if(isMacosArm()) {
            extract("/mac-arm/ffmpeg", file);
            return file.getAbsolutePath();
        }

        else if(isLinuxAmd64()) {
            extract("/linux-amd/ffmpeg", file);
            return file.getAbsolutePath();
        }

        else
        {
            throw new IOException("Unsupported Platform");
        }
    }

    public static String installFFprobe() throws IOException {
        File file = new File(getInstallFolder(), "ffprobe");

        if(file.exists())
        {
            return file.getAbsolutePath();
        }

        else if(isMacosArm()) {
            extract("/mac-arm/ffprobe", file);
            return file.getAbsolutePath();
        }

        else if(isLinuxAmd64()) {
            extract("/linux-amd/ffprobe", file);
            return file.getAbsolutePath();
        }

        else
        {
            throw new IOException("Unsupported Platform");
        }
    }

    public static boolean isMacosArm() {
        return System.getProperty("os.arch").equals("aarch64");
    }

    public static boolean isLinuxAmd64 () {
        return System.getProperty("os.arch").equals("amd64");
    }
}
