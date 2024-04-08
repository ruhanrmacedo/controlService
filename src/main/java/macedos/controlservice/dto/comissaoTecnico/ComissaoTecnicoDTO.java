package macedos.controlservice.dto.comissaoTecnico;

public record ComissaoTecnicoDTO(Long idTecnico, int mes, int ano, boolean bonus) {
    public String bonusStatus() {
        return bonus ? "Com bônus" : "Sem bônus";
    }
}
