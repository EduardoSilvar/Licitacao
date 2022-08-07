/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Anexo;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import org.primefaces.model.file.UploadedFile;
import util.Caracteres;
import util.Msg;

/**
 *
 * @author eduardo
 */
@Stateless
public class AnexoServico implements Serializable {

    @PersistenceContext
    EntityManager em;
    private int cont = 0;
    public static final String REAL_PATH_TMP = "/tmp/Licitacao/uploads/";
    public String REAL_PATH_OPT = "/opt/Licitacao/uploads/";

    public Anexo adicionarAnexo(UploadedFile arquivo) throws FileNotFoundException, SQLException, IOException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSSS");
        String data = dateFormat.format(new Date());

        cont++;
        String nome = data + (cont) + "_" + arquivo.getFileName();

        nome = Caracteres.removeCaracterAnexo(nome);
        nome = nome.trim();
        nome = nome.toLowerCase();
        nome = nome.replaceAll(" ", "_");

        return adicionarArquivo(arquivo, nome, REAL_PATH_OPT);
    }

    public Anexo adicionarArquivo(UploadedFile arquivo, String nome, String caminho) throws FileNotFoundException, SQLException, IOException {
        try {

            Anexo anexo = new Anexo();

            cont++;
            String nomeExibicao = Caracteres.removeCaracterAnexo(arquivo.getFileName()).replace(" ", "_").replace("/", " ").trim();

            anexo.setNome(nome);
            anexo.setNomeExibicao(nomeExibicao);

            // Aqui cria o diretorio caso não exista
            File file = new File(caminho);
            file.mkdirs();

            anexo.setUrl(caminho);

//            if (adicionarArquivoTiff(anexo, arquivo)) {
//                return anexo;
//            }
            String path = anexo.getUrl() + anexo.getNome();
            adicionarArquivo(path, arquivo.getContent());

            return anexo;

        } catch (Exception ex) {
            System.err.println(ex);
            Msg.messagemError("Erro ao salvar anexo!");
            return null;
        }
    }

    private void adicionarArquivo(String caminho, byte[] arquivo) throws IOException {
        gravarArquivo(caminho, arquivo);

        if (extensaoImagem(caminho)) {
            criarMiniatura(caminho);
        }
    }

    /**
     *
     * Cria uma miniatura da imagem existente no caminho informado
     *
     * @param caminhoImagem Caminho completo da imagem Ex: ../imagens/icon.png
     */
    public static void criarMiniatura(String caminhoImagem) {
        try {
            Thumbnails.of(new File(caminhoImagem)).crop(Positions.CENTER).size(100, 100).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        } catch (net.coobird.thumbnailator.tasks.UnsupportedFormatException ex) {
        } catch (IOException ex) {
            System.err.println(ex);
            Logger
                    .getLogger(AnexoServico.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gravarArquivo(String caminho, byte[] arquivo) throws FileNotFoundException, IOException {
        // esse trecho grava o arquivo no diretório
        FileOutputStream fos = new FileOutputStream(caminho);
        fos.write(arquivo);
        fos.close();
    }

    public boolean extensaoImagem(String path) {
        String extensao = null;

        if (path != null) {
            extensao = "";
            if (path.lastIndexOf('.') != -1) {
                extensao = path.substring(path.lastIndexOf('.'));
                if (extensao.startsWith(".")) {
                    extensao = extensao.substring(1);
                }
            }
        }

        if (extensao.equals("doc") || extensao.equals("docx") || extensao.equals("pdf") || extensao.equals("odt")) {
            return false;
        } else {
            return true;
        }
    }

}
