# Guia de desenvolvimento

Este guia mostra como rodar o back-end Spring Boot e o front-end React/Vite juntos em ambiente local.

## Pré-requisitos

- Java 21 ou superior
- Node.js e npm
- Git

## Estrutura do projeto

```text
.
├── app/        # Back-end Spring Boot
├── frontend/  # Front-end React com Vite
└── docs/      # Documentação do projeto
```

## Instalação inicial

Na raiz do projeto, valide o back-end:

```bash
./gradlew build
```

Depois instale as dependências do front-end:

```bash
cd frontend
npm install
```

## Rodando back-end e front-end juntos

Use dois terminais.

Terminal 1, na raiz do projeto:

```bash
./gradlew bootRun
```

O back-end Spring Boot sobe por padrão em:

```text
http://localhost:8080
```

Terminal 2, na pasta `frontend`:

```bash
npm run dev
```

O front-end Vite sobe por padrão em:

```text
http://localhost:5173
```

## Comandos úteis

Back-end:

```bash
./gradlew build
./gradlew test
./gradlew bootRun
```

Front-end:

```bash
cd frontend
npm run dev
npm run build
npm run lint
```

## Observações

- O back-end fica em `app/`, mas os comandos Gradle devem ser executados a partir da raiz do projeto.
- O front-end fica em `frontend/`, e os comandos npm devem ser executados dentro dessa pasta.

