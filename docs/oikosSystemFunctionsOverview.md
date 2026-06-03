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

Este documento apresenta o levantamento detalhado das funções internas do sistema, alinhado à proposta da Atividade 1 e à modelagem definida no diagrama UML (Atividade 3), utilizando exatamente os nomes das classes, interfaces, atributos e métodos adotados no projeto.

O escopo deste levantamento e do diagrama UML associado concentra-se na parte interna do backend: domínio, serviços, gerenciamento e persistência. A camada de API REST não faz parte deste recorte, portanto controllers, requisição/resposta, tratamento de exceções da API, configuração web e a classe de inicialização da aplicação não são detalhados neste documento.

## 2. Visão funcional do sistema

O sistema deve permitir:

1. criar e excluir grupos com proteção por senha;
2. consultar grupos existentes por lista ou identificador;
3. cadastrar e remover Pessoa em um Grupo;
4. cadastrar e remover Evento com pontuação associada;
5. pontuar o Grupo com base no registro de atividade de Pessoa em Evento;
6. definir e redefinir meta de pontuação;
7. acompanhar pontuacaoAtual e metasBatidas;
8. persistir os dados de domínio em JSON;
9. recuperar os dados persistidos entre execuções do sistema.

## 3. Levantamento detalhado das funções por componente

### 3.1 ServicoGrupos

**Responsabilidade geral**
Concentrar casos de uso de gerenciamento de Grupo e coordenar a persistência da lista de grupos.

**Atributos**

- grupos: List<Grupo>
- persistencia: Persistivel<List<Grupo>>

**Funções**

- getListaGrupos(): List<Grupo>  
Descrição: retorna uma cópia da lista de grupos disponíveis no sistema.

- getGrupoPorId(UUID): Grupo  
Descrição: localiza e retorna o grupo correspondente ao UUID informado.
Resultado esperado: retorna o Grupo encontrado ou lança NoSuchElementException se nenhum grupo possuir o identificador informado.

- criarGrupo(String, String): Grupo  
Descrição: cria um novo grupo com nome e senha e o retorna.  
Regras funcionais:  
- nome do grupo não pode ser vazio;
- senha não pode ser vazia;  
- nome do grupo deve ser único, ignorando diferença entre maiúsculas e minúsculas;
- novo grupo deve iniciar com estado consistente (pontuação inicial, meta inicial e estruturas de gerenciamento);  
- após criar o grupo, o sistema persiste a lista de grupos.

- excluirGrupo(UUID, String): void  
Descrição: exclui um grupo a partir de seu UUID, mediante confirmação de senha.  
Regras funcionais:  
- o grupo deve existir;  
- a senha informada deve autorizar a exclusão;
- após excluir o grupo, o sistema persiste a lista de grupos.

- pontuar(UUID, UUID, UUID): void
Descrição: registra uma ação de uma pessoa em um evento dentro de um grupo específico.
Parâmetros:
- grupoId: UUID do grupo pontuado;
- pessoaId: UUID da pessoa que realizou a atividade;
- eventoId: UUID do evento realizado.
Regras funcionais:
- o grupo deve existir;
- a pessoa deve pertencer ao grupo;
- o evento deve pertencer ao grupo;
- a pontuação é delegada para Grupo.pontuar(Pessoa, Evento);
- após pontuar, o sistema persiste a lista de grupos.

- redefinirMeta(UUID, int): Grupo
Descrição: redefine a meta de pontuação de um grupo específico e retorna o grupo atualizado.
Regras funcionais:
- a nova meta deve ser maior que zero;
- o grupo deve existir;
- após redefinir a meta, o sistema persiste a lista de grupos.

- salvar(): String
Descrição: delega a gravação da lista de grupos para persistencia.salvar(this.grupos).
Resultado esperado: retorna o nome do arquivo persistido.

- recuperar(): void
Descrição: recupera a lista de grupos usando persistencia.recuperar() e restaura a referência de origem dos gerenciadores internos.
Resultado esperado: grupos previamente salvos voltam a ficar disponíveis na aplicação.

### 3.2 Grupo

**Responsabilidade geral**
Classe central do domínio, responsável por autenticação local do grupo, pontuação coletiva, metas e acesso aos gerenciadores.

**Atributos**

- nome: String
- senha: String
- gerenciadorPessoas: Gerenciador<Pessoa>
- gerenciadorEventos: Gerenciador<Evento>
- meta: int
- pontuacaoAtual: int
- metasBatidas: int

**Funções**

- getNome(): String
Descrição: retorna o nome do grupo.

- setNome(String): void
Descrição: altera o nome do grupo.

- autenticarSenha(String): boolean
Descrição: compara a senha informada com a senha do grupo para validação.

- redefinirSenha(String, String): void
Descrição: altera a senha do grupo.
Regra funcional: senha antiga deve ser validada antes da troca.

- getMeta(): int
Descrição: retorna a meta atual do grupo.

- setMeta(int): void
Descrição: define diretamente a meta atual do grupo.

- redefinirMeta(int): void
Descrição: altera a meta atual do grupo.

- getPontuacaoAtual(): int
Descrição: retorna a pontuação acumulada do grupo.

- setPontuacaoAtual(int): void
Descrição: define diretamente a pontuação atual do grupo.

- getMetasBatidas(): int
Descrição: retorna o total de metas já atingidas pelo grupo.

- setMetasBatidas(int): void
Descrição: define diretamente o total de metas batidas.

- getClassificacao(): String
Descrição: retorna a classificação textual do grupo (Iniciante, Bronze, Prata, Ouro, Diamante) com base em metasBatidas. Delega para o método classificar() da interface Classificavel.

- getGerenciadorPessoas(): Gerenciador<Pessoa>
Descrição: fornece acesso ao gerenciador de pessoas do grupo.

- setGerenciadorPessoas(Gerenciador<Pessoa>): void
Descrição: define o gerenciador de pessoas do grupo.

- getGerenciadorEventos(): Gerenciador<Evento>
Descrição: fornece acesso ao gerenciador de eventos do grupo.

- setGerenciadorEventos(Gerenciador<Evento>): void
Descrição: define o gerenciador de eventos do grupo.

- adicionarPontos(int): void
Descrição: incrementa a pontuação do grupo. Se o acumulado atingir a meta, incrementa metasBatidas e mantém o saldo excedente em pontuacaoAtual.

- pontuar(Pessoa, Evento): void  
Descrição: registra uma ação de Pessoa em Evento e atualiza a pontuação do grupo.  
Regras funcionais:  
- Pessoa e Evento devem pertencer ao contexto do Grupo;  
- delega o cálculo de pontos para adicionarPontos().

- reiniciarPontos(): void  
Descrição: reinicia o acumulador de pontos do grupo para novo ciclo de acompanhamento.

### 3.3 Gerenciador

**Responsabilidade geral**  
Classe genérica concreta para gerenciamento de coleções de entidades de um Grupo.

**Atributos**

- entidades: List<TipoEntidade>
- grupoOrigem: Grupo

**Funções**

- getListaEntidades(): List<TipoEntidade>  
Descrição: retorna a lista completa de entidades gerenciadas.

- setListaEntidades(List<TipoEntidade>): void
Descrição: define a lista de entidades, permitindo restauração de dados persistidos.

- setGrupoOrigem(Grupo): void
Descrição: restaura a referência ao grupo de origem após a desserialização.

- getPorId(UUID): TipoEntidade  
Descrição: busca uma entidade por identificador. Retorna null se não encontrada.

- adicionarEntidade(TipoEntidade): void  
Descrição: adiciona uma entidade à coleção.

- removerEntidade(UUID): void  
Descrição: remove uma entidade da coleção por UUID.

### 3.4 Entidade (abstrata)

**Responsabilidade geral**  
Padronizar identificação única de objetos de domínio.

**Atributos**

- id: UUID

**Funções**

- getId(): UUID  
Descrição: retorna o identificador único desta entidade.

- setId(UUID): void
Descrição: define o identificador da entidade, permitindo restauração de dados persistidos.

### 3.5 Pessoa

**Responsabilidade geral**  
Representar o membro do grupo que realiza atividades.

**Atributos**

- nome: String

**Funções**

- getNome(): String  
Descrição: retorna o nome da pessoa.

### 3.6 Evento

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

### 3.7 ServicoEntidades (abstrata)

**Responsabilidade geral**  
Serviço genérico que encapsula operações CRUD comuns sobre entidades, operando sobre as entidades vinculadas a um grupo específico.

**Atributos**

- servicoGrupos: ServicoGrupos

**Funções**

- getLista(UUID): List<TipoEntidade
Descrição: retorna a lista de entidades do grupo informado.

- getPorId(UUID, UUID): TipoEntidade  
Descrição: busca uma entidade pelo UUID no grupo informado. Lança NoSuchElementException se não encontrada.

- adicionar(UUID, TipoEntidade): void (abstrato)
Descrição: subclasses implementam para aplicar validações específicas antes de adicionar uma entidade ao grupo informado.

- remover(UUID, UUID): void  
Descrição: valida existência, remove a entidade do grupo informado e persiste a alteração.

- getGerenciadorPorGrupoId(UUID): Gerenciador<TipoEntidade> (protegido, abstrato)
Descrição: retorna o gerenciador do grupo informado correspondente ao tipo da subclasse.

### 3.8 ServicoPessoas

**Responsabilidade geral**
Especializa ServicoEntidades implementando a validação específica ao cadastrar uma nova Pessoa em um grupo.

**Funções**

- adicionar(UUID, Pessoa): void
Descrição: valida que o nome não seja nulo ou vazio antes de adicionar ao grupo informado.

- getGerenciadorPorGrupoId(UUID): Gerenciador<Pessoa> (protegido)
Descrição: retorna o gerenciador de pessoas do grupo informado.

### 3.9 ServicoEventos

**Responsabilidade geral**  
Especializa ServicoEntidades implementando as validações específicas ao cadastrar um novo Evento em um grupo.

**Funções**

- adicionar(UUID, Evento): void  
Descrição: valida que o nome não seja vazio e que os pontos sejam maiores que zero antes de adicionar ao grupo informado.

- getGerenciadorPorGrupoId(UUID): Gerenciador<Evento> (protegido)  
Descrição: retorna o gerenciador de eventos do grupo informado.

### 3.10 PersistenciaJson

**Responsabilidade geral**  
Implementar a persistência genérica em arquivo JSON, desacoplando os serviços dos detalhes de leitura e escrita.

**Atributos**

- arquivo: File
- tipoDado: TypeReference<TipoDado>
- valorPadrao: Supplier<TipoDado>
- mapper: ObjectMapper

**Funções**

- salvar(TipoDado): String
Descrição: serializa os dados recebidos e grava no arquivo configurado.
Resultado esperado: retorna o nome do arquivo gravado.

- recuperar(): TipoDado
Descrição: lê o arquivo configurado e desserializa seu conteúdo.
Resultado esperado: retorna os dados recuperados ou o valor padrão quando o arquivo não existir, estiver vazio ou não puder ser lido corretamente.

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

- implementada por Grupo, que expõe o resultado via getClassificacao().

### 4.4 Persistivel

**Funções**

- salvar(TipoDado): String  
Descrição: contrato para salvar dados.

- recuperar(): TipoDado
Descrição: contrato para recuperar dados salvos.

**Aplicação no sistema**

- implementada por PersistenciaJson<TipoDado>, permitindo que a lógica de persistência seja reutilizável e desacoplada dos serviços.

## 5. Fluxos funcionais principais

### 5.1 Criação de grupo

1. Usuário informa nome e senha.
2. Sistema delega para ServicoGrupos.criarGrupo(String, String).
3. Novo Grupo é adicionado à lista de grupos.
4. ServicoGrupos.salvar() persiste a lista atualizada.

### 5.2 Entrada em grupo existente

1. Usuário seleciona um grupo existente.
2. Sistema valida senha via autenticarSenha(String).
3. O grupo autenticado pode ser usado nas operações seguintes por meio de seu UUID.

### 5.3 Gestão de pessoas

1. Usuário informa Pessoa.
2. ServicoPessoas executa adicionar(UUID, Pessoa) ou remover(UUID, UUID).
3. A operação é refletida no gerenciador de pessoas do grupo informado.
4. A alteração é persistida.

### 5.4 Gestão de eventos

1. Usuário informa Evento.
2. ServicoEventos executa adicionar(UUID, Evento) ou remover(UUID, UUID).
3. A operação é refletida no gerenciador de eventos do grupo informado.
4. A alteração é persistida.

### 5.5 Registro de atividade e pontuação

1. Usuário informa Pessoa e Evento.
2. Sistema executa ServicoGrupos.pontuar(grupoId, pessoaId, eventoId).
3. pontuacaoAtual é incrementada conforme pontos do Evento.
4. Sistema verifica evolução em relação à meta e atualiza metasBatidas quando aplicável.
5. A alteração é persistida.

### 5.6 Persistência de dados

1. ServicoGrupos mantém a lista de grupos como raiz do estado persistido.
2. ServicoGrupos.salvar() delega a gravação para PersistenciaJson<List<Grupo>>.
3. Os dados são gravados em data/grupos.json.
4. Na inicialização, ServicoGrupos.recuperar() recupera a lista salva.
5. Após recuperar os dados, o serviço restaura grupoOrigem nos gerenciadores de pessoas e eventos.

## 6. Regras funcionais consolidadas

- Um Grupo só pode ser acessado mediante autenticação.
- Pessoa e Evento devem estar vinculados ao Grupo que está sendo operado.
- Toda operação de pontuação altera pontuacaoAtual de forma consistente.
- Meta pode ser redefinida conforme política do grupo, desde que seja maior que zero.
- O estado de progresso do grupo inclui meta, pontuacaoAtual e metasBatidas.
- Dados precisam ser persistidos entre execuções do sistema.
- A persistência deve gravar a lista de grupos em data/grupos.json.
