public class clsPagina {
    private int numero;
    private int tamanho; // Tamanho da página em bytes
    private int processoID; // ID do processo ao qual a página pertence

    public clsPagina(int numero, int tamanho, int processoID) {
        this.numero = numero;
        this.tamanho = tamanho;
        this.processoID = processoID;
    }

    // Getters e setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getProcessoID() {
        return processoID;
    }

    public void setProcessoID(int processoID) {
        this.processoID = processoID;
    }

    // Método para obter o processo associado à página
    public clsProcesso getProcesso() {
        // Aqui você deve implementar a lógica para obter o objeto clsProcesso correspondente ao processoID
        // Por exemplo, você pode pesquisar em uma lista de processos ou em outra estrutura de dados
        // Neste exemplo, retornarei null
        return null;
    }
}
