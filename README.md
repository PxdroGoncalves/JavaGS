# AlertaOrbital — API Restful Quarkus

Sistema de Monitoramento de Desastres Naturais via Satélites.
Projeto desenvolvido para o Global Solution (FIAP).

O AlertaOrbital é uma plataforma backend construída com o framework Quarkus para centralizar, gerenciar e disparar alertas sobre ocorrências de desastres naturais captados por monitoramento satelital. A API integra-se com o Oracle Database e consome dados públicos de eventos em tempo real.

---

## Pré-Requisitos

* Java JDK: Versão 21+
* Maven: Versão 3.9+
* Quarkus: Versão 3.x
* Oracle Database: Versão 19c+ (FIAP)
* IDE: IntelliJ IDEA / Eclipse / VS Code

---

## Configuração do Banco

1. Execute o script SQL no Oracle (arquivo alertaorbital.sql).
2. Edite as credenciais em ConexaoFactory.java:

```java
return DriverManager.getConnection(
    "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl",
    "SEU_USUARIO",
    "SUA_SENHA"
);
A API também realiza integração externa com o protocolo NASA EONET (Earth Observatory Natural Event Tracker) para enriquecimento de dados globais de desastres.

Executar
Modo desenvolvimento (hot reload):

Bash
./mvnw quarkus:dev
API disponível em: http://localhost:8080
Console de desenvolvimento Quarkus em: http://localhost:8080/q/dev

Build JAR e execução:

Bash
./mvnw package -DskipTests
java -jar target/quarkus-app/quarkus-run.jar
Endpoints
Ocorrências (/api/ocorrencias)

GET /api/ocorrencias — Listar todas (Status 200)

GET /api/ocorrencias/{id} — Buscar por id (Status 200/404)

GET /api/ocorrencias/status/{status} — Filtrar por status (Status 200/400)

GET /api/ocorrencias/regiao/{id} — Filtrar por região (Status 200)

GET /api/ocorrencias/satelite/{id} — Filtrar por satélite (Status 200)

GET /api/ocorrencias/{id}/satelites — Satélites vinculados (Status 200)

POST /api/ocorrencias — Criar (Status 201/400)

PUT /api/ocorrencias/{id} — Atualizar completo (Status 200/400)

PATCH /api/ocorrencias/{id}/status — Atualizar status (Status 200/400)

POST /api/ocorrencias/{id}/satelites — Vincular satélite (Status 201/400)

DELETE /api/ocorrencias/{id}/satelites/{idSat} — Desvincular satélite (Status 204/400)

DELETE /api/ocorrencias/{id} — Deletar (Status 204/500)

Regiões (/api/regioes)

GET /api/regioes — Listar regiões (Status 200)

GET /api/regioes/{id} — Buscar região (Status 200/404)

POST /api/regioes — Criar região (Status 201/400)

PUT /api/regioes/{id} — Atualizar região (Status 200/400)

DELETE /api/regioes/{id} — Deletar região (Status 204/500)

Tipos de Desastre (/api/tipos-desastre)

GET /api/tipos-desastre — Listar tipos (Status 200)

GET /api/tipos-desastre/{id} — Buscar tipo (Status 200/404)

POST /api/tipos-desastre — Criar tipo (Status 201/400)

PUT /api/tipos-desastre/{id} — Atualizar tipo (Status 200/400)

DELETE /api/tipos-desastre/{id} — Deletar tipo (Status 204/500)

Usuários (/api/usuarios)

GET /api/usuarios — Listar usuários (Status 200)

GET /api/usuarios/{id} — Buscar usuário (Status 200/404)

POST /api/usuarios — Criar usuário (Status 201/400)

PUT /api/usuarios/{id} — Atualizar usuário (Status 200/400)

DELETE /api/usuarios/{id} — Deletar usuário (Status 204/500)

Satélites (/api/satelites)

GET /api/satelites — Listar satélites (Status 200)

GET /api/satelites/operacionais — Somente operacionais (Status 200)

GET /api/satelites/{id} — Buscar satélite (Status 200/404)

POST /api/satelites — Criar satélite (Status 201/400)

PUT /api/satelites/{id} — Atualizar satélite (Status 200/400)

DELETE /api/satelites/{id} — Deletar satélite (Status 204/500)

Alertas (/api/alertas)

GET /api/alertas — Listar alertas (Status 200)

GET /api/alertas/{id} — Buscar alerta (Status 200/404)

GET /api/alertas/usuario/{id} — Alertas por operador (Status 200)

GET /api/alertas/ocorrencia/{id} — Alertas por ocorrência (Status 200)

POST /api/alertas — Criar alerta (Status 201/400)

PUT /api/alertas/{id} — Atualizar alerta (Status 200/400)

DELETE /api/alertas/{id} — Deletar alerta (Status 204/500)

Relatórios e Integrações (/api/relatorio)

GET /api/relatorio — Relatório JSON completo (Status 200)

GET /api/relatorio/nasa-eonet — Eventos NASA EONET (Status 200/502)

Exemplos
Criar Ocorrência
POST /api/ocorrencias

JSON
{
  "dataInicio": "2026-06-01",
  "descricao": "Enchente no Rio Tietê",
  "status": "ATIVO",
  "idRegiao": 1,
  "idTipo": 1
}
Response (201 Created):

JSON
{
  "id": 1,
  "dataInicio": "2026-06-01",
  "descricao": "Enchente no Rio Tietê",
  "status": "ATIVO",
  "idRegiao": 1,
  "idTipo": 1
}
Atualizar Status
PATCH /api/ocorrencias/1/status

JSON
{ "status": "CONTROLADO" }
Vincular Satélite
POST /api/ocorrencias/1/satelites

JSON
{ "idSatelite": "6", "dataDeteccao": "2026-06-01" }
Criar Alerta
POST /api/alertas

JSON
{
  "mensagem": "Nível do rio acima do normal.",
  "idOcorrencia": 1,
  "idUsuario": 1
}
Regras de Negócio
Status: progressão obrigatória ATIVO -> CONTROLADO -> RESOLVIDO (sem retrocesso).

Nível de risco: BAIXO, MEDIO, ALTO ou CRITICO.

Satélite operacional: S ou N.

Restrição de vínculo: Não é possível vincular o mesmo satélite duas vezes à mesma ocorrência.