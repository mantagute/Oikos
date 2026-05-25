# Oikos: Where Faith Finds Home

**Arthur Marques Midon | Marcos Boson Mota | João Gabriel Iuzviak Mantagute | Gabriel Castro Andrade**

---

## Objetivo

Desenvolver uma plataforma de rede social voltada ao rastreamento de hábitos (*habit tracker*) com foco exclusivo em grupos religiosos. O sistema visa automatizar a organização e o monitoramento de atividades coletivas, utilizando o engajamento social como ferramenta de motivação e retenção dos membros.

---

## Requisitos Funcionais

- A plataforma permite a **criação de grupos com senhas**. Dentro de um grupo, os usuários podem adicionar ou remover pessoas, definir eventos (com pontuação associada) e metas a serem cumpridas coletivamente.
- A qualquer momento, os usuários podem informar ao sistema que uma pessoa realizou determinado evento. A plataforma contabiliza e registra os avanços do grupo, salvando essas informações em arquivos permanentes.
- O sistema identifica automaticamente quando um grupo bate uma meta. O número de metas cumpridas é armazenado e exibido, e bater uma meta resulta na evolução do **status** do grupo.

---

## Requisitos de Interface

- **Página inicial:** lista de grupos, onde o usuário pode criar novos grupos (definindo nome e senha) ou entrar em grupos existentes.
- **Página do grupo:** exibe o progresso em relação às metas definidas, o número de vezes que o grupo bateu suas metas, e recomenda a redefinição de metas quando atingidas. Também dá acesso ao:
  - Gerenciador de Pessoas
  - Gerenciador de Metas
  - Gerenciador de Eventos
  - Botão **"Registrar Atividade"**
- **Gerenciador de Pessoas:** visualizar, adicionar e remover membros.
- **Gerenciador de Eventos:** visualizar, criar ou excluir eventos, com pontuação definida para cada um.
- **Gerenciador de Metas:** definir ou redefinir a meta do grupo.
- **Registrar Atividade:** selecionar uma pessoa e um evento, registrando a atividade e somando a pontuação ao acumulador do grupo.

---

## Tecnologia

| Camada | Tecnologia |
|---|---|
| Front-end | React.js |
| Back-end | Java + Spring Boot |
| Persistência | Arquivos JSON |
| Ambiente | Localhost (sem deploy em nuvem) |

- **Front-end:** interface componentizada e responsiva, com atualização dinâmica de métricas e progresso.
- **Back-end:** modelagem de domínio com padrões de POO para gerenciar grupos, eventos e usuários.
- **Persistência:** armazenamento via arquivos JSON, substituindo banco de dados relacional para este protótipo, garantindo portabilidade entre ambientes de teste.
- **Comunicação:** Front-end e Back-end se comunicam via API REST.

## Desenvolvimento local

Consulte o [guia de desenvolvimento](docs/development.md) para rodar back-end e front-end juntos em ambiente local.

---

## Diferencial de Mercado

Existem dezenas de *habit trackers* no mercado (como Habitica, Loop e Strides), mas a grande maioria é **hiper-individualista**, focada em performance pessoal, produtividade corporativa ou competição.

O Oikos atua em uma lacuna pouco explorada: o **engajamento comunitário e a disciplina espiritual coletiva**.

Dentro do contexto religioso, práticas como leitura de textos sagrados, orações diárias, jejuns ou ações de caridade não são meras tarefas em uma checklist — são atos de devoção e comunhão. Nosso diferencial está em **transferir a métrica de sucesso do Indivíduo para o Todo**.

A plataforma transforma a formação de hábitos solitários em uma **responsabilidade compartilhada**. Em vez da pressão isolada do *"eu preciso bater minha meta"*, cria-se o sentimento de *"eu estou edificando a minha comunidade"*. Esse modelo de *accountability* mútua e fraterna já é profundamente enraizado na cultura das instituições religiosas, garantindo à plataforma uma taxa de retenção e um propósito de uso superiores aos aplicativos genéricos do mercado.

---

## Monetização

O modelo de sustentabilidade foi desenhado para espelhar o ciclo de contribuição autossustentável presente nas comunidades religiosas, onde a manutenção da plataforma é vista como uma extensão natural da prática da fé e do suporte à comunidade.

### 1. Modelo Institucional: Church-as-a-Service (CaaS)

Voltado a paróquias, dioceses e organizações religiosas, oferecendo infraestrutura digital para gestão de hierarquias e subgrupos:

- **Gestão Centralizada:** painéis analíticos para acompanhar o engajamento da comunidade em tempo real.
- **Difusão de Eventos:** ferramentas avançadas para divulgação de festas, celebrações e ações sociais.
- **Valor Agregado:** o software se torna uma ferramenta vital de administração e comunicação, justificando o investimento institucional como otimizador da missão evangelizadora.

### 2. Ecossistema de Generosidade: Contribuição Direta dos Fiéis

A plataforma adota um modelo de **Patrocínio Comunitário**, permitindo que os próprios usuários contribuam diretamente para a manutenção do ecossistema.

Inspirado no conceito de dízimos e ofertas, o suporte financeiro é tratado como um ato de cuidado com a *"Casa Digital"* (Oikos). Essa via elimina a necessidade de anúncios invasivos ou venda de dados, preservando a sacralidade do ambiente e garantindo que o software seja sustentado por aqueles que ele serve.
