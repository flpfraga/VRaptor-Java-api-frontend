package br.com.smartcrono.teste.arquivo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Arquivo {

	public void upload(String file, String fileName, InputStream loadFile) throws IOException {
		String urlFile = file + "/" + fileName;
		File novo = new File(urlFile);
		FileOutputStream saida = new FileOutputStream(novo);
		copiar(loadFile, saida);

	}

	private void copiar(InputStream origem, OutputStream destino) throws IOException {
		int bite = 0;
		byte[] maxSize = new byte[1024 * 16]; // 16KB
		while ((bite = origem.read(maxSize)) >= 0) {
			destino.write(maxSize, 0, bite);
		}
	}

}
