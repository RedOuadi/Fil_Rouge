import {Exercice} from "./exercice.model";

export interface ProgrammeEntrainement {
  id: number;
  nom: string;
  description: string;
  coachId: number;
  exercices: Exercice[];
}
