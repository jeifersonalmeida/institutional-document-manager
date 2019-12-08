package app.models.Ordinance;

public enum OrdinanceType {
    COMMISSION,
    INSTITUTIONAL_PROJECT,
    ORDINANCE;

    @Override
    public String toString() {
        if (this == COMMISSION) {
            return "Comissão";
        } else if (this == INSTITUTIONAL_PROJECT) {
            return "Projeto Institucional";
        } else if (this == ORDINANCE) {
            return "Portaria";
        } else {
            return "Indefinido";
        }
    }
}
