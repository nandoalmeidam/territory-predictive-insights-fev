# PLUVIA — Monitoramento Inteligente de Chuvas


**FIAP | Global Solution 2º Semestre de 2026**  

**Disciplina:** Programação Orientada a Objetos  

**Tema:** Territory Predictive Insights — CitizenTech  

**Turma:** 2CCPG


## 1. Sobre o projeto

O **Pluvia** é um sistema de monitoramento territorial desenvolvido em Java para acompanhar condições ambientais relacionadas a chuvas e possíveis riscos de alagamento.

A solução utiliza dados de sensores, estratégias de previsão e limites configuráveis para gerar alertas e apoiar o acompanhamento preventivo de regiões monitoradas.

O projeto foi desenvolvido como aplicação de console, com persistência em banco de dados e exportação de informações em CSV.


## 2. Problema e público beneficiado

Chuvas intensas e alterações nas condições ambientais podem aumentar os riscos de alagamentos e outros problemas territoriais.

O Pluvia busca facilitar a identificação de situações que merecem atenção, organizando medições, previsões e alertas em um único sistema.

**Público beneficiado:**

- Equipes de monitoramento territorial.
- Órgãos municipais e agentes de defesa civil.
- Comunidades localizadas em regiões vulneráveis a chuvas intensas.
- Gestores que necessitam consultar históricos ambientais.

O sistema é um protótipo acadêmico e não substitui serviços oficiais de monitoramento ou emissão de alertas.


## 3. Funcionalidades

O menu principal disponibiliza:


| Opção | Funcionalidade |
|---|---|
| 1 | Realizar leitura dos sensores |
| 2 | Consultar previsão de chuva |
| 3 | Consultar histórico de medições |
| 4 | Consultar histórico de alertas |
| 5 | Consultar dados territoriais |
| 6 | Consultar status do sistema |
| 7 | Exportar dados para CSV |
| 0 | Encerrar o programa |

O sistema permite consultar medições anteriores e exportar históricos para arquivos CSV, incluindo seleção do local de salvamento.


## 4. Tecnologias utilizadas

- Java 17.

- Spring Boot.

- Maven e Maven Wrapper.

- Spring Data JPA.

- Banco de dados H2 persistente em arquivo.

- JUnit 5.

- Mockito.

- Git e GitHub.


## 5. Programação Orientada a Objetos

### Abstração e herança

A classe abstrata `ComponenteTerritorial` representa características comuns de componentes territoriais.

A classe `RegiaoMonitorada` representa uma implementação concreta dessa abstração.

### Interfaces e polimorfismo

A interface `Sensor` define o contrato dos sensores.

Implementações disponíveis:

- `SensorChuva`
- `SensorNivelRio`
- `SensorUmidadeSolo`

### Encapsulamento

A classe `DadosTerritorio` concentra informações territoriais e aplica regras de acesso e validação aos seus dados.

### Estratégias de previsão

A classe abstrata `EstrategiaPrevisao` define o comportamento de previsão.

Implementações:

- `PrevisaoMediaMovel`
- `PrevisaoComparacao`

As estratégias permitem utilizar diferentes formas de previsão por meio do polimorfismo.

### Padrões de criação

**Factory:** `SensorFactory` centraliza a criação de sensores.

**Builder:** `AlertaBuilder` organiza a construção de objetos de alerta e valida informações necessárias.

### Exceções personalizadas

O projeto implementa:

- `DadosInvalidosException`: representa informações inválidas.
- `FonteIndisponivelException`: representa fontes de dados indisponíveis.

As exceções são tratadas no menu para apresentar mensagens compreensíveis ao usuário.


## 6. Persistência de dados

O Pluvia utiliza Spring Data JPA com banco H2 em arquivo.

A configuração principal está em:

`src/main/resources/application.properties`

Configurações relevantes:

```properties

spring.datasource.url=jdbc:h2:file:./dados/pluvia

spring.jpa.hibernate.ddl-auto=update

```

As medições e os alertas são armazenados para consulta posterior.

O uso do H2 em arquivo permite manter registros entre execuções, desde que a aplicação utilize o mesmo diretório de trabalho.

A pasta `dados/` é gerada localmente e não deve ser enviada ao GitHub.


## 7. Alertas e previsões

O monitoramento utiliza medições ambientais e limites configuráveis para identificar condições de atenção.

O sistema trabalha com os níveis:

- **ATENÇÃO**
- **ALERTA**
- **CRÍTICO**

Os alertas auxiliam na identificação de possíveis situações de risco. Sua interpretação deve considerar a qualidade dos dados disponíveis e o contexto territorial.


## 8. Como executar

### Pré-requisitos

- JDK 17.
- Git para clonar o repositório.
- Terminal Windows, Git Bash ou ambiente compatível.

Não é necessário instalar o Maven globalmente, pois o projeto inclui o Maven Wrapper.

### Clonar o projeto

```bash

git clone -b dev https://github.com/nandoalmeidam/territory-predictive-insights-fev.git

cd territory-predictive-insights-fev

```

### Executar os testes

No Windows:

```bat

mvnw.cmd clean test

```

No Git Bash:

```bash

./mvnw clean test

```

### Executar a aplicação

No Windows:

```bat

mvnw.cmd spring-boot:run

```

No Git Bash:

```bash

./mvnw spring-boot:run

```

O menu interativo será exibido no terminal.

**Observação:** para selecionar visualmente o destino dos arquivos CSV, execute a aplicação em um ambiente Windows com interface gráfica disponível.


## 9. Testes automatizados

O projeto utiliza JUnit 5 e Mockito.

Os testes abrangem componentes como:

- Validação de medições.
- Configuração do sistema.
- Estratégias de previsão.
- Comportamento de sensores.
- Serviços de alertas.
- Repositórios simulados com Mockito.
- Exportação CSV.

O teste `MedicaoRepositoryTest` utiliza um repositório simulado para verificar consultas sem depender de uma conexão real com o banco H2.

O projeto também utiliza `assertThrows` para verificar situações de erro.

Na última validação registrada durante o desenvolvimento, foram executados **13 testes com sucesso**.


## 10. Exportação CSV

A opção 7 do menu permite exportar:

- Histórico de medições.
- Histórico de alertas.
- Ambos os históricos.

Os arquivos utilizam formato CSV, separador por ponto e vírgula e codificação compatível com caracteres acentuados.

O usuário pode selecionar o destino e o nome dos arquivos.


## 11. Reflexão crítica

O Pluvia demonstra como conceitos de orientação a objetos podem organizar um sistema de monitoramento territorial. Entretanto, uma previsão produzida por software não representa uma garantia sobre acontecimentos futuros.

A confiabilidade das análises depende diretamente da quantidade, qualidade e atualização das medições disponíveis. Um sensor pode apresentar falhas, interrupções, valores incorretos ou leituras que não representem adequadamente toda uma região.

Quando existem poucas medições ou informações insuficientes, o sistema deve evitar transmitir uma falsa sensação de precisão. Nesses casos, é preferível informar a limitação dos dados ou não emitir uma conclusão definitiva.

Também é importante reconhecer que diferentes territórios apresentam características próprias. Um mesmo volume de chuva pode produzir consequências distintas dependendo da drenagem, relevo, ocupação urbana e condições anteriores do solo.

Os alertas devem apoiar a análise humana, não substituí-la. Decisões que envolvam segurança da população precisam considerar informações oficiais, profissionais responsáveis e outras fontes confiáveis.

Como evolução futura, o projeto poderia incorporar sensores físicos, fontes meteorológicas oficiais, análises geográficas e mecanismos adicionais de validação dos dados.


## 12. Organização da equipe

| Integrante | RM | Responsabilidades |
|---|---|---|
| Fernando de Almeida Godoi Martines | 564820 | Integração do projeto, menu, configuração Maven, Git/GitHub e documentação |
| Gabriel Ber Soares Tarone | 563520 | Persistência, previsões, alertas e testes |
| Guilherme de Freitas Salgado | 562494 | Modelagem de domínio, sensores e exceções |


A equipe organizou o desenvolvimento em componentes separados, buscando facilitar a manutenção, os testes e a integração do sistema.

O desenvolvimento foi realizado de forma colaborativa, com divisão de responsabilidades e utilização do GitHub para versionamento do código.


## 13. Uso de inteligência artificial

Ferramentas de inteligência artificial foram utilizadas como apoio ao desenvolvimento e à revisão do projeto, incluindo esclarecimento de conceitos, análise de erros, organização da documentação e sugestões de implementação.

As sugestões foram avaliadas durante o desenvolvimento e verificadas por meio da execução da aplicação, testes automatizados e inspeção do código.

A responsabilidade pelas decisões de implementação e pela entrega permanece com a equipe.

---

**FIAP — Global Solution 2026 | Pluvia — Monitoramento Inteligente de Chuvas**