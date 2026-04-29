// src/app/core/providers/provide-markdown.ts
import { importProvidersFrom } from '@angular/core';
import { MarkdownModule } from 'ngx-markdown';
import { HttpClientModule } from '@angular/common/http';

export const provideMarkdown = () =>
  importProvidersFrom(
    HttpClientModule, // requis par ngx-markdown
    MarkdownModule.forRoot()
  );
