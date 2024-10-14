import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActiviteService} from "../../services/activite.service";
import {Activite} from "../../models/activite.model";


@Component({
  selector: 'app-activite-update',
  templateUrl: './activite-update.component.html',
  styleUrls: ['./activite-update.component.css']
})
export class ActiviteUpdateComponent implements OnInit {
  activiteForm: FormGroup;
  imageFile: File | null = null;
  videoFile: File | null = null;
  isLoading = false;
  activiteId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private activiteService: ActiviteService,
    protected router: Router
  ) {
    this.activiteForm = this.fb.group({
      date: ['', Validators.required],
      pas: [0, [Validators.required, Validators.min(0)]],
      distance: [0, [Validators.required, Validators.min(0)]],
      caloriesBrulees: [0, Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.activiteId = +params.get('id')!;
      this.loadActivite(this.activiteId);
    });
  }

  loadActivite(id: number): void {
    this.activiteService.getActiviteById(id).subscribe({
      next: (activite) => {
        this.activiteForm.patchValue(activite);
      },
      error: (err) => {
        console.error('Error loading activity:', err);
      }
    });
  }

  onImageSelected(event: any): void {
    this.imageFile = event.target.files[0];
  }

  onVideoSelected(event: any): void {
    this.videoFile = event.target.files[0];
  }

  onSubmit(): void {
    if (this.activiteForm.invalid || !this.activiteId) return;

    this.isLoading = true;
    const activite: Activite = this.activiteForm.value;

    this.activiteService.updateActivite(this.activiteId, activite, this.imageFile ?? undefined, this.videoFile ?? undefined).subscribe({
      next: () => {
        this.router.navigate(['/coach/activite-list', activite.utilisateurId]);
      },
      error: (err) => {
        console.error('Failed to update activity', err);
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}
