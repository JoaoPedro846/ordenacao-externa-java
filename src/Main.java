import gerador.GeradorNumerico;
import ordenacao.OrdenacaoExterna;

void main(){

    String home = System.getProperty("user.home");
    File pasta = new File(home, "Documents/ProgramacaoExterna");

    if(!pasta.exists()) {
        boolean novaPasta = pasta.mkdir();
        if(novaPasta){
            System.out.println("Pasta criada com sucesso!");
        }
        else{
            System.out.println("Não foi possível criar a página");
            return;
        }
    }

    File arquivo = new File(pasta, "arquivo.txt");

    GeradorNumerico.gerador(arquivo, 193);

    OrdenacaoExterna ordenacaoExterna = new OrdenacaoExterna(arquivo);

    ordenacaoExterna.ordenar(20);
}