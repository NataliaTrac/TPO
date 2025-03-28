package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class Futil {
  public static void processDir(String dirName, String resultFileName) {
    
    Path rootDir = Paths.get(dirName);
    Path outFile = Paths.get(resultFileName);

    // usuwam plik jeśli istnieje, tworzę nowy
    try {
      Files.deleteIfExists(outFile);
      Files.createFile(outFile);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    // otwieram kanal do zapisu
    try (FileChannel outChannel = FileChannel.open(outFile,
        EnumSet.of(StandardOpenOption.WRITE))) {

    // przechodzę przez wszystkie pliki w katalogu rekurencja!!
      Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
        // dla każdego pliku odczytuję jego zawartość, konwertuję na UTF-8 i zapisuję do pliku wynikowego
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // jesli plik jest zwyczajny to go przetwarzam 
         if (Files.isRegularFile(file)) {
            try (FileChannel inChannel = FileChannel.open(file, StandardOpenOption.READ)) {
              // bufor na zawartość pliku wejściowego jest alokowany na jego rozmiar
              ByteBuffer buffer = ByteBuffer.allocate((int) inChannel.size());
              // wczytuje zawartosc 
              inChannel.read(buffer);
              // przestawiam na odczyt
              buffer.flip();
              // dekoduje, a potem koduje zawartosc pliku
              String content = Charset.forName("Cp1250").decode(buffer).toString();
              ByteBuffer utf8Data = Charset.forName("UTF-8").encode(content);
              outChannel.write(utf8Data);
            }
          }
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
