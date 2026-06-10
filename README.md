# Oikos: Where Faith Finds Home

**Arthur Marques Midon (247271) | Marcos Boson Mota (169328) | João Gabriel Iuzviak Mantagute (285570) | Gabriel Castro Andrade (280869)**

---

## O que é o Oikos

O Oikos é uma plataforma de acompanhamento coletivo de hábitos voltada a grupos religiosos. Enquanto a maioria dos *habit trackers* do mercado é hiper-individualista — focada em performance pessoal, produtividade ou competição —, o Oikos atua em uma lacuna pouco explorada: o **engajamento comunitário e a disciplina espiritual coletiva**.

Dentro do contexto religioso, práticas como leitura de textos sagrados, orações diárias, jejuns ou ações de caridade não são meras tarefas em uma checklist — são atos de devoção e comunhão. O Oikos **transfere a métrica de sucesso do indivíduo para o todo**: em vez da pressão isolada do *"eu preciso bater minha meta"*, cria-se o sentimento de *"eu estou edificando minha comunidade"*.

A plataforma é organizada em dois níveis de acesso:

- **Grupos** — a unidade central do sistema. Cada grupo cadastra seus membros e eventos, registra atividades realizadas, acumula pontuação coletiva e evolui de classificação conforme bate suas metas.
- **Paróquias** — entidades de escopo maior que vinculam grupos, acompanham métricas consolidadas de toda a comunidade e se comunicam com os grupos via notificações.

---

## Regras de Negócio

### Grupos e Pontuação

- Um grupo é criado com nome e senha únicos. A senha é necessária para acessar, modificar ou excluir o grupo.
- Cada grupo possui uma **meta de pontuação** (padrão: 1000 pontos) e um **acumulador** que cresce conforme os membros realizam eventos.
- Quando o acumulador atinge a meta, o grupo **bate a meta**: o contador de metas batidas incrementa e o saldo excedente é retido para o próximo ciclo. A meta pode ser redefinida a qualquer momento.
- O número de metas batidas determina a **classificação** do grupo:

| Metas batidas | Classificação |
|---|---|
| 0 | Iniciante |
| ≥ 1 | Bronze |
| ≥ 3 | Prata |
| ≥ 5 | Ouro |
| ≥ 10 | Diamante |

### Eventos e Pessoas

- Eventos representam atividades pontuáveis (ex: "Leitura Bíblica", "Oração matinal"). Cada evento tem um nome e um valor em pontos, definidos pelo próprio grupo.
- Pessoas são os membros cadastrados no grupo. Para registrar uma atividade, basta selecionar uma pessoa e um evento — o sistema soma os pontos ao acumulador do grupo.
- Tanto eventos quanto pessoas pertencem exclusivamente ao grupo em que foram criados.

### Paróquias e Vínculos

- Uma paróquia se vincula a grupos por meio de um **fluxo de solicitação e aceite**: a paróquia envia uma solicitação, que aparece como notificação no grupo. O grupo aceita ou ignora.
- Após o vínculo, a paróquia passa a visualizar as métricas do grupo no seu dashboard e pode enviar notificações para grupos individuais ou em broadcast.
- O vínculo pode ser desfeito pela paróquia a qualquer momento.

### Persistência

- Todo o estado do sistema é salvo automaticamente em arquivos JSON (`data/grupos.json` e `data/paroquias.json`) a cada operação de escrita. Os dados sobrevivem ao reinício da aplicação.

---

## Como Rodar

### Pré-requisitos

- Java 21
- Node.js (versão LTS recomendada)
- Gradle (o projeto inclui o wrapper `./gradlew`)

### Passo a passo

**1. Clone o repositório:**

```bash
git clone git@github.com:mantagute/Oikos.git
cd Oikos
```

**2. Inicie o back-end** (a partir da raiz do projeto):

```bash
./gradlew bootRun
```

Mantenha esse terminal aberto. O servidor sobe em `http://localhost:8080`.

**3. Em um novo terminal, inicie o front-end:**

```bash
cd frontend
npm install
npm run dev
```

Acesse o endereço exibido no terminal — normalmente `http://localhost:5173` — em um navegador de preferência baseado em Chromium.

> Ambos os terminais precisam permanecer abertos para o sistema funcionar.

---

## Por que não há deploy em nuvem

O Oikos é um protótipo acadêmico. A persistência foi implementada em arquivos JSON locais, o que é suficiente para demonstrar os conceitos de gravação e leitura exigidos pela proposta, mas não é adequado para um ambiente multi-instância em nuvem — onde múltiplas réplicas do servidor escreveriam no mesmo arquivo simultaneamente, causando corrupção de dados.

Um deploy em produção exigiria substituir o `PersistenciaJson` por um banco de dados relacional ou NoSQL, o que está fora do escopo deste trabalho. A arquitetura foi desenhada para tornar essa troca simples: basta implementar uma nova classe que satisfaça a interface `Persistivel<T>`.

---

## Tecnologia

| Camada | Tecnologia |
|---|---|
| Front-end | React.js + Vite |
| Back-end | Java 21 + Spring Boot 3 |
| Persistência | Arquivos JSON (via Jackson) |
| Build | Gradle (Kotlin DSL) |
| Testes | JUnit 5 + JaCoCo |
| Comunicação | API REST (JSON) |

O front-end é uma SPA componentizada e responsiva. O back-end expõe uma API REST consumida pelo front via Axios. A camada de domínio — classes, interfaces e serviços — é completamente independente do Spring, o que facilita os testes unitários.

---

## Diagrama UML

![Diagrama de Classes UML](docs/uml.png)

> O diagrama cobre as camadas de domínio, serviços, gerenciamento e persistência. A camada de API REST (controllers, DTOs, tratamento de exceções) foi omitida intencionalmente, conforme descrito na documentação da Atividade 2.

---

## Atendimento à Proposta

A seguir, como cada requisito da proposta é atendido no sistema:

### Relacionamentos com ênfase em estruturas polimórficas

O `Gerenciador<TipoEntidade>` é uma classe genérica concreta que opera sobre qualquer subtipo de `Entidade` — `Pessoa`, `Evento`, `Notificacao` e até `Grupo` (dentro de `Paroquia`). `ServicoEntidades<TipoEntidade>` é abstrata e polimórfica: `ServicoPessoas`, `ServicoEventos` e `ServicoNotificacoes` herdam seu comportamento CRUD e sobrescrevem apenas as validações específicas. `ServicoEscopoMaior<TipoEntidade>` segue a mesma lógica para `ServicoGrupos` e `ServicoParoquias`.

### Interfaces e Classes Abstratas

**Classes abstratas:**
- `Entidade` — base de todo objeto de domínio, fornece o UUID automático.
- `ServicoEntidades<T>` — encapsula CRUD genérico para entidades pertencentes a um grupo.
- `ServicoEscopoMaior<T>` — encapsula CRUD, persistência e autenticação para entidades de escopo maior.

**Interfaces:**
- `Autenticavel` — contrato de verificação de senha, implementado por `Grupo` e `Paroquia`.
- `Classificavel` — contrato de classificação com método `default` embutido, implementado por `Grupo`.
- `Pontuavel` — contrato de acúmulo e reinício de pontuação, implementado por `Grupo`.
- `Persistivel<T>` — contrato de gravação e recuperação de dados, implementado por `PersistenciaJson<T>`.

### Interface Gráfica

Interface web construída em React.js, responsiva, com roteamento client-side via React Router. Inclui componentes reutilizáveis (sistema de design próprio prefixado `*Oikos`), toasts de feedback, diálogos de confirmação, barra de progresso animada e skeleton loading.

### Tratamento de Exceção

Além das exceções padrão do Java (`IllegalArgumentException`, `NoSuchElementException`, `SecurityException`), o sistema define e trata:

- Validações de negócio lançadas como `IllegalArgumentException` com mensagens específicas (nome vazio, pontos inválidos, meta inválida, senha incorreta, grupo já vinculado, solicitação de vínculo duplicada).
- Erros de autenticação lançados como `SecurityException` (senha incorreta na exclusão).
- O `ApiExceptionHandler` captura essas exceções globalmente e retorna respostas HTTP estruturadas com timestamp, status, mensagem e path — tornando os erros legíveis tanto para o front-end quanto para depuração.

### Arquivos (Gravação e Leitura)

`PersistenciaJson<T>` implementa a interface `Persistivel<T>` e usa o Jackson `ObjectMapper` para serializar e desserializar listas completas de entidades em JSON. A gravação ocorre automaticamente após toda operação de escrita. A recuperação ocorre na inicialização da aplicação via `@PostConstruct`, restaurando inclusive referências transientes (`grupoOrigem` nos gerenciadores) que não são serializadas.

Arquivos gerados: `data/grupos.json` e `data/paroquias.json`.

## Monetização

O modelo de sustentabilidade foi desenhado para espelhar o ciclo de contribuição autossustentável presente nas comunidades religiosas, onde a manutenção da plataforma é vista como uma extensão natural da prática da fé e do suporte à comunidade.

### Modelo Institucional: Church-as-a-Service (CaaS)

Voltado a paróquias, dioceses e organizações religiosas, oferecendo infraestrutura digital para gestão de hierarquias e subgrupos:

- **Gestão Centralizada:** painéis analíticos para acompanhar o engajamento da comunidade em tempo real.
- **Difusão de Eventos:** ferramentas avançadas para divulgação de festas, celebrações e ações sociais.
- **Valor Agregado:** o software se torna uma ferramenta vital de administração e comunicação, justificando o investimento institucional como otimizador da missão evangelizadora.

### Ecossistema de Generosidade: Contribuição Direta dos Fiéis

A plataforma adota um modelo de **Patrocínio Comunitário**, permitindo que os próprios usuários contribuam diretamente para a manutenção do ecossistema.

Inspirado no conceito de dízimos e ofertas, o suporte financeiro é tratado como um ato de cuidado com a *"Casa Digital"* (Oikos). Essa via elimina a necessidade de anúncios invasivos ou venda de dados, preservando a sacralidade do ambiente e garantindo que o software seja sustentado por aqueles que ele serve.

---

*Oikos — Where Faith Finds Home*