import 'zone.js/node';

import { APP_BASE_HREF } from '@angular/common';
import { CommonEngine } from '@angular/ssr/node';
import express, { Request, Response } from 'express';
import { existsSync } from 'node:fs';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';
import bootstrap from './main.server';
import httpProxy from 'http-proxy';

// The Express app is exported so that it can be used by serverless Functions.
export function app(): express.Express {
  const server = express();
  const distFolder = join(process.cwd(), 'dist/frontend/browser');
  const indexHtml = existsSync(join(distFolder, 'index.original.html'))
    ? join(distFolder, 'index.original.html')
    : join(distFolder, 'index.html');

  const commonEngine = new CommonEngine();
  const apiProxy = httpProxy.createProxyServer({ target: 'http://youprono_api:8080', changeOrigin: true });

  server.use('/api', (req: Request, res: Response) => {
    apiProxy.web(req, res, undefined as any, (err: unknown) => {
      console.error('Erreur de proxy /api → backend :', (err as Error).message);
      res.status(500).send('Erreur de proxy vers l’API');
    });
  });
  server.set('view engine', 'html');
  server.set('views', distFolder);

  // Serve static files from /browser
  server.get('*.*', express.static(distFolder, {
    maxAge: '1y'
  }));

  // All regular routes use the Angular engine
  server.get('*', (req, res, next) => {
    const { protocol, originalUrl, baseUrl, headers } = req;

    commonEngine
      .render({
        bootstrap,
        documentFilePath: indexHtml,
        url: `${protocol}://${headers.host}${originalUrl}`,
        publicPath: distFolder,
        providers: [{ provide: APP_BASE_HREF, useValue: baseUrl }],
      })
      .then((html) => res.send(html))
      .catch((err) => next(err));
  });

  return server;
}


function run(): void {
  const port = 4000;

  // Start up the Node server
  const server = app();
  server.listen(port, '0.0.0.0', () => {
    console.log(`Node Express server listening on http://localhost:${port}`);
  });
}

// Only start the server when this file is the entry point, not when imported as a module.
if (process.argv[1] === fileURLToPath(import.meta.url)) {
  run();
}

export default bootstrap;
