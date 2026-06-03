# AlertaOrbital — API Restful Quarkus

Sistema de Monitoramento de Desastres Naturais via Satélites.

---

## Pré-Requisitos

| Ferramenta | Versão |
|---|---|
| Java JDK | 21+ |
| Maven | 3.9+ |
| Oracle Database | 19c+ (FIAP) |
| IDE | IntelliJ IDEA / Eclipse / VS Code |

---

## ⚙️ Configuração do Banco

1. Execute o script SQL no Oracle (arquivo `alertaorbital.sql`)
2. Edite as credenciais em `ConexaoFactory.java`:

```java
return DriverManager.getConnection(
    "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl",
    "SEU_USUARIO",
    "SUA_SENHA"
);
```

---

## ▶️ Executar

```bash
# Modo desenvolvimento (hot reload)
./mvnw quarkus:dev

# Build JAR
./mvnw package -DskipTests
java -jar target/quarkus-app/quarkus-run.jar
```

API disponível em: **http://localhost:8080**

---

## 📋 Endpoints

| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| GET | /api/ocorrencias | Listar todas | 200 |
| GET | /api/ocorrencias/{id} | Buscar por id | 200/404 |
| GET | /api/ocorrencias/status/{status} | Filtrar por status | 200/400 |
| GET | /api/ocorrencias/regiao/{id} | Filtrar por região | 200 |
| GET | /api/ocorrencias/satelite/{id} | Filtrar por satélite | 200 |
| GET | /api/ocorrencias/{id}/satelites | Satélites vinculados | 200 |
| POST | /api/ocorrencias | Criar | 201/400 |
| PUT | /api/ocorrencias/{id} | Atualizar completo | 200/400 |
| PATCH | /api/ocorrencias/{id}/status | Atualizar status | 200/400 |
| POST | /api/ocorrencias/{id}/satelites | Vincular satélite | 201/400 |
| DELETE | /api/ocorrencias/{id}/satelites/{idSat} | Desvincular satélite | 204/400 |
| DELETE | /api/ocorrencias/{id} | Deletar | 204/500 |
| GET | /api/regioes | Listar regiões | 200 |
| GET | /api/regioes/{id} | Buscar região | 200/404 |
| POST | /api/regioes | Criar região | 201/400 |
| PUT | /api/regioes/{id} | Atualizar região | 200/400 |
| DELETE | /api/regioes/{id} | Deletar região | 204/500 |
| GET | /api/tipos-desastre | Listar tipos | 200 |
| GET | /api/tipos-desastre/{id} | Buscar tipo | 200/404 |
| POST | /api/tipos-desastre | Criar tipo | 201/400 |
| PUT | /api/tipos-desastre/{id} | Atualizar tipo | 200/400 |
| DELETE | /api/tipos-desastre/{id} | Deletar tipo | 204/500 |
| GET | /api/usuarios | Listar usuários | 200 |
| GET | /api/usuarios/{id} | Buscar usuário | 200/404 |
| POST | /api/usuarios | Criar usuário | 201/400 |
| PUT | /api/usuarios/{id} | Atualizar usuário | 200/400 |
| DELETE | /api/usuarios/{id} | Deletar usuário | 204/500 |
| GET | /api/satelites | Listar satélites | 200 |
| GET | /api/satelites/operacionais | Somente operacionais | 200 |
| GET | /api/satelites/{id} | Buscar satélite | 200/404 |
| POST | /api/satelites | Criar satélite | 201/400 |
| PUT | /api/satelites/{id} | Atualizar satélite | 200/400 |
| DELETE | /api/satelites/{id} | Deletar satélite | 204/500 |
| GET | /api/alertas | Listar alertas | 200 |
| GET | /api/alertas/{id} | Buscar alerta | 200/404 |
| GET | /api/alertas/usuario/{id} | Alertas por operador | 200 |
| GET | /api/alertas/ocorrencia/{id} | Alertas por ocorrência | 200 |
| POST | /api/alertas | Criar alerta | 201/400 |
| PUT | /api/alertas/{id} | Atualizar alerta | 200/400 |
| DELETE | /api/alertas/{id} | Deletar alerta | 204/500 |
| GET | /api/relatorio | Relatório JSON completo | 200 |
| GET | /api/relatorio/nasa-eonet | Eventos NASA EONET | 200/502 |

---

## Exemplos

### Criar Ocorrência
```json
POST /api/ocorrencias
{
  "dataInicio": "2026-06-01",
  "descricao": "Enchente no Rio Tietê",
  "status": "ATIVO",
  "idRegiao": 1,
  "idTipo": 1
}
```

### Atualizar Status
```json
PATCH /api/ocorrencias/1/status
{ "status": "CONTROLADO" }
```

### Vincular Satélite
```json
POST /api/ocorrencias/1/satelites
{ "idSatelite": "6", "dataDeteccao": "2026-06-01" }
```

### Criar Alerta
```json
POST /api/alertas
{
  "mensagem": "Nível do rio acima do normal.",
  "idOcorrencia": 1,
  "idUsuario": 1
}
```

---

## Regras de Negócio
- **Status**: progressão obrigatória `ATIVO → CONTROLADO → RESOLVIDO` (sem retrocesso)
- **Nível de risco**: `BAIXO | MEDIO | ALTO | CRITICO`
- **Satélite operacional**: `S | N`
- Não é possível vincular o mesmo satélite duas vezes à mesma ocorrência
