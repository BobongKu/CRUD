package bobong.crud.global.file.service;

import bobong.crud.global.file.exception.FileErrorCode;
import bobong.crud.global.file.exception.FileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public String save(MultipartFile multipartFile) throws Exception {
        String filePath = fileDir + UUID.randomUUID();

        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e){
            //파일 저장 에러
            throw new FileException(FileErrorCode.FILE_CAN_NOT_SAVE);
        }

        return filePath;
    }

    @Override
    public void delete(String filePath) {
        File file = new File(filePath);

        if(!file.exists()) return;

        if(!file.delete()) throw new FileException(FileErrorCode.FILE_CAN_NOT_DELETE);
    }
}
