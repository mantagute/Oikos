# Oikos - Where Faith Finds Home.
## Atividade 2 — Levantamento de todas as funções que devem ser executadas no sistema

**Integrantes**  
Arthur Marques Midon (247271)  
Marcos Boson Mota (169328)  
João Gabriel Iuzviak Mantagute (285570)  
Gabriel Castro Andrade (280869)

---

## 1. Introdução

O Oikos é uma plataforma de acompanhamento coletivo de hábitos para grupos religiosos. O sistema foi projetado para permitir que um grupo organize seus membros, eventos e metas de progresso, registrando atividades realizadas e convertendo essas ações em pontuação acumulada do grupo.

Este documento apresenta o levantamento detalhado das funções do sistema, alinhado à proposta da Atividade 1 e à modelagem definida no diagrama UML (Atividade 3), utilizando exatamente os nomes das classes, interfaces, atributos e métodos adotados no projeto.

## 2. Visão funcional do sistema

O sistema deve permitir:

1. criar e excluir grupos com proteção por senha;
2. selecionar e acessar um Grupo específico;
3. cadastrar e remover Pessoa em um Grupo;
4. cadastrar e remover Evento com pontuação associada;
5. pontuar o Grupo com base no registro de atividade de Pessoa em Evento;
6. definir e redefinir meta de pontuação;
7. acompanhar pontuacaoAtual e metasBatidas;
8. persistir os dados de domínio em JSON.

## 3. Levantamento detalhado das funções por componente

### 3.1 App

**Responsabilidade geral**  
Controlar o fluxo principal da aplicação e manter o estado de navegação.

**Atributos**

- servicoGrupos: ServicoGrupos
- servicoPessoas: ServicoPessoas
- servicoEventos: ServicoEventos

**Funções**

- iniciar(): void  
Descrição: inicia a aplicação e organiza o fluxo principal.  
Resultado esperado: aplicação pronta para listar grupos, selecionar grupo e acessar funcionalidades.

**Comportamento funcional esperado do App**

- acionar servicoGrupos para operações de criação, consulta e exclusão de grupos;
- manter referência ao Grupo selecionado para operações de domínio;
- servir como ponto de integração com a interface gráfica.

### 3.2 ServicoGrupos

**Responsabilidade geral**  
Concentrar casos de uso de gerenciamento de Grupo.

**Funções**

- getListaGrupos(): List<Grupo>  
Descrição: retorna a lista de grupos disponíveis no sistema.

- getGrupoPorId(UUID): Grupo  
Descrição: localiza e retorna o grupo correspondente ao UUID informado.

- criarGrupo(String, String): Grupo  
Descrição: cria um novo grupo com nome e senha e o retorna.  
Regras funcionais:  
- nome do grupo deve ser válido e único;  
- senha não pode ser vazia;  
- novo grupo deve iniciar com estado consistente (pontuação inicial, meta inicial e estruturas de gerenciamento);  
- o grupo recém-criado é automaticamente marcado como ativo.

- excluirGrupo(UUID, String): void  
Descrição: exclui um grupo a partir de seu UUID, mediante confirmação de senha.  
Regras funcionais:  
- o grupo deve existir;  
- a senha informada deve autorizar a exclusão.

- selecionarGrupo(UUID): void  
Descrição: define qual grupo está ativo no sistema.

- getGrupoSelecionado(): Grupo  
Descrição: retorna o grupo atualmente selecionado ou lança exceção se nenhum estiver ativo.

- pontuar(UUID, UUID): void  
Descrição: registra uma ação de uma pessoa em um evento e delega a lógica de pontuação para o grupo ativo.

### 3.3 Grupo

**Responsabilidade geral**  
Classe central do domínio, responsável por autenticação local do grupo, pontuação coletiva, metas e acesso aos gerenciadores.

**Atributos**

- nome: String
- senha: String
- gerenciadorPessoas: Gerenciador<Pessoa>
- gerenciadorEventos: Gerenciador<Evento>
- metaPontos: int
- pontuacaoAtual: int
- metasBatidas: int

**Funções**

- getNome(): String  
Descrição: retorna o nome do grupo.

- autenticarSenha(String): boolean  
Descrição: compara a senha informada com a senha do grupo para validação.

- redefinirSenha(String, String): void  
Descrição: altera a senha do grupo.  
Regra funcional: senha antiga deve ser validada antes da troca.

- getMeta(): int  
Descrição: retorna a meta atual do grupo (valor de `metaPontos`).

- getPontuacaoAtual(): int  
Descrição: retorna a pontuação acumulada do grupo.

- getMetasBatidas(): int  
Descrição: retorna o total de metas já atingidas pelo grupo.

- getClassificacao(): String  
Descrição: retorna a classificação textual do grupo (Iniciante, Bronze, Prata, Ouro, Diamante) com base em `metasBatidas`. Delega para o método `classificar()` da interface `Classificavel`.

- redefinirMeta(int): void  
Descrição: altera a meta atual do grupo.

- getGerenciadorPessoas(): Gerenciador<Pessoa>  
Descrição: fornece acesso ao gerenciador de pessoas do grupo.

- getGerenciadorEventos(): Gerenciador<Evento>  
Descrição: fornece acesso ao gerenciador de eventos do grupo.

- adicionarPontos(int): void  
Descrição: incrementa a pontuação do grupo. Se o acumulado atingir `metaPontos`, incrementa `metasBatidas` e reinicia o saldo com o excedente.

- pontuar(Pessoa, Evento): void  
Descrição: registra uma ação de Pessoa em Evento e atualiza a pontuação do grupo.  
Regras funcionais:  
- Pessoa e Evento devem pertencer ao contexto do Grupo;  
- delega o cálculo de pontos para `adicionarPontos()`.

- reiniciarPontos(): void  
Descrição: reinicia o acumulador de pontos do grupo para novo ciclo de acompanhamento.

### 3.4 Gerenciador

**Responsabilidade geral**  
Classe genérica concreta para gerenciamento de coleções de entidades de um Grupo.

**Atributos**

- entidades: List<TipoEntidade>
- grupoOrigem: Grupo

**Funções**

- getListaEntidades(): List<TipoEntidade>  
Descrição: retorna a lista completa de entidades gerenciadas.

- getPorId(UUID): TipoEntidade  
Descrição: busca uma entidade por identificador. Retorna `null` se não encontrada.

- adicionarEntidade(TipoEntidade): void  
Descrição: adiciona uma entidade à coleção.

- removerEntidade(UUID): void  
Descrição: remove uma entidade da coleção por UUID.

### 3.5 Entidade (abstrata)

**Responsabilidade geral**  
Padronizar identificação única de objetos de domínio.

**Atributos**

- Id: UUID

**Funções**

- getId(): UUID  
Descrição: retorna o identificador único desta entidade.

### 3.6 Pessoa

**Responsabilidade geral**  
Representar o membro do grupo que realiza atividades.

**Atributos**

- nome: String

**Funções**

- getNome(): String  
Descrição: retorna o nome da pessoa.

### 3.7 Evento

**Responsabilidade geral**  
Representar atividade pontuável do grupo.

**Atributos**

- nome: String
- pontos: int

**Funções**

- getNome(): String  
Descrição: retorna o nome do evento.

- getPontos(): int  
Descrição: retorna o valor de pontos associado ao evento.

### 3.8 HolderGrupoSelecionado

**Responsabilidade geral**  
Classe utilitária que mantém, em memória, o UUID do "grupo ativo".

**Atributos**

- grupoSelecionadoId: UUID

**Funções**

- setGrupoSelecionadoId(UUID): void  
Descrição: define o UUID do grupo ativo.

- getGrupoSelecionadoId(): UUID  
Descrição: retorna o UUID do grupo ativo, ou `null` se nenhum estiver selecionado.

- clear(): void  
Descrição: limpa a seleção, removendo o UUID armazenado.

### 3.9 ServicoEntidades (abstrata)

**Responsabilidade geral**  
Serviço genérico que encapsula operações CRUD comuns sobre entidades, operando sempre sobre o grupo atualmente selecionado via `ServicoGrupos`.

**Atributos**

- servicoGrupos: ServicoGrupos

**Funções**

- getLista(): List<TipoEntidade>  
Descrição: retorna a lista de entidades do grupo ativo, delegando para `getGerenciadorAtual()`.

- getPorId(UUID): TipoEntidade  
Descrição: busca uma entidade pelo UUID no grupo ativo. Lança `NoSuchElementException` se não encontrada.

- adicionar(TipoEntidade): void (abstrato)  
Descrição: subclasses implementam para aplicar validações específicas antes de adicionar.

- remover(UUID): void  
Descrição: valida existência e remove a entidade do grupo ativo.

- getGerenciadorAtual(): Gerenciador<TipoEntidade> (protegido, abstrato)  
Descrição: retorna o gerenciador do grupo ativo correspondente ao tipo da subclasse.

### 3.10 ServicoPessoas

**Responsabilidade geral**  
Especializa `ServicoEntidades` implementando a validação específica ao cadastrar uma nova `Pessoa` no grupo ativo.

**Funções**

- adicionar(Pessoa): void  
Descrição: valida que o nome não seja nulo ou vazio antes de adicionar ao grupo ativo.

- getGerenciadorAtual(): Gerenciador<Pessoa> (protegido)  
Descrição: retorna o gerenciador de pessoas do grupo selecionado.

### 3.11 ServicoEventos

**Responsabilidade geral**  
Especializa `ServicoEntidades` implementando as validações específicas ao cadastrar um novo `Evento` no grupo ativo.

**Funções**

- adicionar(Evento): void  
Descrição: valida que o nome não seja vazio e que os pontos sejam maiores que zero antes de adicionar ao grupo ativo.

- getGerenciadorAtual(): Gerenciador<Evento> (protegido)  
Descrição: retorna o gerenciador de eventos do grupo selecionado.

## 4. Interfaces e funções contratuais


### 4.1 Pontuavel

**Funções**

- adicionarPontos(valor: int): void  
Descrição: contrato para incremento de pontuação.

- reiniciarPontos(): void  
Descrição: contrato para reset de pontuação.

- getPontuacaoAtual():int
Descrição: contrato para consultar pontuação.

**Aplicação no sistema**

- implementada por Grupo para consolidar o comportamento de pontuação coletiva.

### 4.2 Autenticavel

**Funções**

- autenticarSenha(String): boolean  
Descrição: contrato para autenticação por senha.

**Aplicação no sistema**

- implementada por Grupo para validar acesso e ações protegidas.

### 4.3 Classificavel

**Funções**

- classificar(int): String (default)  
Descrição: retorna uma classificação textual baseada no número de metas batidas.  
Regras: Iniciante (0), Bronze (≥1), Prata (≥3), Ouro (≥5), Diamante (≥10).

**Aplicação no sistema**

- implementada por Grupo, que expe o resultado via `getClassificacao()`.

### 4.4 Persistivel

**Funções**

- toJson(): String  
Descrição: serializa objeto para formato JSON.

- fromJson(json: String): void  
Descrição: restaura o estado do objeto a partir de JSON.

**Aplicação no sistema**

- implementada por Grupo, Pessoa e Evento, permitindo que essas classes sejam serializadas em JSON e restauradas a partir de JSON para leitura e gravação dos dados de domínio em arquivos permanentes.

## 5. Fluxos funcionais principais

### 5.1 Criação de grupo

1. Usuário informa nome e senha.
2. App delega para ServicoGrupos.criarGrupo(String, String).
3. Novo Grupo é adicionado à ListaGrupos.

### 5.2 Entrada em grupo existente

1. Usuário seleciona grupo da lista.
2. Sistema valida senha via autenticarSenha(String).
3. GrupoSelecionado passa a referenciar o grupo autenticado.

### 5.3 Gestão de pessoas

1. Usuário usa o ServicoPessoas.
2. Executa adicionar(Pessoa) ou remover(UUID).
3. A operação é refletida no gerenciador de pessoas do grupo selecionado.

### 5.4 Gestão de eventos

1. Usuário usa o ServicoEventos.
2. Executa adicionar(Evento) ou remover(UUID).
3. A operação é refletida no gerenciador de eventos do grupo selecionado.

### 5.5 Registro de atividade e pontuação

1. Usuário informa os IDs de Pessoa e Evento.
2. Sistema executa ServicoGrupos.pontuar(pessoaId, eventoId).
3. pontuacaoAtual é incrementada conforme pontos do Evento.
4. Sistema verifica evolução em relação à meta e atualiza metasBatidas quando aplicável.

### 5.6 Persistência de dados

1. Dados do domínio são serializados com toJson().
2. Dados são gravados em arquivo JSON.
3. Na inicialização, fromJson(json: String) restaura o estado salvo.

## 6. Regras funcionais consolidadas

- Um Grupo só pode ser acessado mediante autenticação.
- Pessoa e Evento devem estar vinculados ao Grupo que está sendo operado.
- Toda operação de pontuação altera pontuacaoAtual de forma consistente.
- Meta pode ser redefinida conforme política do grupo.
- O estado de progresso do grupo inclui meta, pontuacaoAtual e metasBatidas.
- Dados precisam ser persistidos entre execuções do sistema.
