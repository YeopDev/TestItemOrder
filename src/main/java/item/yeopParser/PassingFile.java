package item.yeopParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Objects.isNull;

public record PassingFile(String fileName) {

    private static final String DEFAULT_PATH = "./src/main/resources/";

    public PassingFile{
        if(isNull(fileName)||fileName.toLowerCase().equals("null")||fileName.equals("")){
            throw new IllegalArgumentException("파일이름이 잘못되었습니다.");
        }
    }

    public List<String> passingExcute() throws IOException {
        return Files.readAllLines(Paths.get(DEFAULT_PATH + fileName));
    }

}
