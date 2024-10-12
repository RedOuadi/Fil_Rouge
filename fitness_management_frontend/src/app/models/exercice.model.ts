import {Image} from "./image.model";
import {Video} from "./video.model";
export interface Exercice {
  id: number;
  nom: string;
  description: string;
  duree: number;
  niveau: string;
  caloriesBrulees: number;
  exerciceImage?: Image;
  exerciceVideo?: Video;  // Assuming Video will be created later
  programmeId?: number;
}
