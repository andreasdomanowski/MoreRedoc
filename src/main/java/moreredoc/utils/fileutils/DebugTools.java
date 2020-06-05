package moreredoc.utils.fileutils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DebugTools {

    private static final Random RANDOM = new Random();

    private DebugTools() {
    }

    public static <E extends Comparable> void sortCopyAndWriteItToFile(List<E> in, String filePath) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();
        List<E> listCopy = new ArrayList<>(in);

        Collections.sort(listCopy);

        listCopy.forEach(x -> resultBuilder.append(x).append(System.lineSeparator()));

        FileUtils.writeStringToFile(new File(filePath), resultBuilder.toString(), (String) null);
    }

    public static String generateRandomString(int length) {
        // from https://www.baeldung.com/java-random-string
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        return RANDOM.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}

