# 🧾 Order Processing System
### Backend Architecture • Java • Spring Boot • Hexagonal • Outbox • SAGA

## 📌 Visão Geral

Este projeto demonstra a construção de um **backend moderno, resiliente e orientado a eventos**, utilizando **Java e Spring Boot**, com foco em **qualidade de código, decisões arquiteturais conscientes e confiabilidade transacional**.

O sistema foi implementado como um **monólito modular estruturado em Arquitetura Hexagonal**, preparado para **evoluir para microsserviços** sem reescrita do domínio.

> 🎯 **Objetivo:** Demonstrar maturidade técnica em arquitetura backend, mensageria, consistência eventual e boas práticas adotadas em sistemas de missão crítica.

---

## 🧠 Problema que o Projeto Resolve

Em sistemas distribuídos, é comum enfrentar problemas como:

- Perda de eventos em falhas entre banco e mensageria
- Uso incorreto de transações distribuídas
- Alto acoplamento entre camadas
- Dificuldade de testes e evolução

Este projeto aborda esses problemas aplicando **Outbox Pattern + SAGA**, garantindo:

- Consistência entre persistência e publicação de eventos
- Processamento assíncrono confiável
- Código desacoplado e altamente testável

---

## 🏗️ Arquitetura

### 🔷 Estilo Arquitetural

- **Arquitetura Hexagonal (Ports and Adapters)**
- Domínio isolado de frameworks e infraestrutura
- Dependências sempre apontando **para dentro**

```
domain
 ├── model
 ├── service
 └── port

application
 ├── usecase
 └── port

infrastructure
 ├── adapter
 │   ├── in
 │   └── out
 ├── outbox
 └── config
```

---

## 🔄 Fluxo de Negócio (SAGA)

1. Pedido é criado e validado
2. Pedido entra no status **PENDING**
3. Evento `ORDER_CREATED_EVENT` é salvo na Outbox
4. Outbox Relay publica o evento no RabbitMQ
5. Listener inicia a SAGA:
   - Processa pedido
   - Processa pagamento
   - Conclui pedido
6. Em caso de falha, executa **ação compensatória (Cancelamento)**

---

## 📦 Outbox Pattern

Para garantir consistência entre banco de dados e mensageria:

- O evento é persistido **na mesma transação do pedido**
- Um **scheduler assíncrono** publica eventos pendentes
- Eventos só são marcados como processados após publicação bem-sucedida

✔️ Sem transações distribuídas  
✔️ Sem perda de mensagens  
✔️ Alta confiabilidade  

---

## 🧩 Status do Pedido

O pedido segue um fluxo simples e explícito:

```
PENDING → PROCESSING → COMPLETED
            ↓
        CANCELLED
```

---

## ⚙️ Stack Tecnológica

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- RabbitMQ
- Docker & Docker Compose
- Jackson
- JUnit 5 & Mockito

---

## 🚀 Execução Local

### Pré-requisitos

- Docker
- Docker Compose

### Subir a aplicação

```bash
docker-compose up -d
```

---

## 🎯 O que este projeto demonstra

✔️ Pensamento arquitetural  
✔️ Uso correto de Outbox Pattern  
✔️ Implementação de SAGA  
✔️ Código limpo, testável e escalável  

---

## 👤 Autor

**Ismael Härter Dewes**  
Backend Engineer • Java • Spring Boot • Arquitetura de Software
