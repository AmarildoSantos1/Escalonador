public class clsFrame {
    private int numero;
    private boolean ocupado; // Indica se o frame está ocupado por uma página
    private clsPagina pagina; // Página armazenada no frame

    public clsFrame(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.pagina = null;
    }

    // Getters e setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public clsPagina getPagina() {
        return pagina;
    }

    public void setPagina(clsPagina pagina) {
        this.pagina = pagina;
    }
}