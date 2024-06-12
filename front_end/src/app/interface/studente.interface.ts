import { Indirizzo } from "./indirizzo.interface";
import { User } from "./user.interface";

export interface Studente extends User {
    nome: string;
    cognome: string;
    dataDiNascita: Date,
    codiceFiscale: string,
    indirizzo: Indirizzo
}
