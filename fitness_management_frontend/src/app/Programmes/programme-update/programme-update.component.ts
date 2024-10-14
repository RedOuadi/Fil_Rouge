import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProgrammeEntrainementService } from '../../services/programme-entrainement.service';
import { ProgrammeEntrainement } from '../../models/programmeentrainement.model';

@Component({
  selector: 'app-programme-update',
  templateUrl: './programme-update.component.html',
  styleUrls: ['./programme-update.component.css']
})
export class ProgrammeUpdateComponent implements OnInit {
  programme: ProgrammeEntrainement | null = null;
  isLoading = true;

  constructor(
    private programmeService: ProgrammeEntrainementService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const programmeId = +this.route.snapshot.paramMap.get('id')!;
    this.loadProgramme(programmeId);
  }

  loadProgramme(programmeId: number): void {
    this.programmeService.getProgrammeById(programmeId).subscribe({
      next: (data) => {
        this.programme = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement du programme', err);
        this.isLoading = false;
      }
    });
  }

  updateProgramme(): void {
    if (this.programme) {
      this.programmeService.updateProgramme(this.programme.id, this.programme).subscribe({
        next: (updatedProgramme) => {
          console.log('Programme mis à jour:', updatedProgramme);
          this.router.navigate(['/coach/programme-list']);
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour du programme', err);
        }
      });
    }
  }
}
