import {Image} from "./image.model";
import {Video} from "./video.model";
export interface Activite {
  id: number;
  date: Date;
  pas: number;
  distance: number;
  caloriesBrulees: number;
  utilisateurId: number;
  activiteImage?: Image;
  activiteVideo?: Video;  // Assuming Video will be created later
}
