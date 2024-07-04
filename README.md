# AdotaPetApi

AdotaPet é um sistema desenvolvido para facilitar a adoção de pets, aplicando boas práticas de desenvolvimento para garantir um código limpo, eficiente e escalável.

## Modificações Realizadas

### Código Internacionalizado

Todo o código foi modificado para o inglês, seguindo boas práticas de código para melhorar a legibilidade e padronização.

### Relacionamentos Performáticos

Os relacionamentos entre as entidades foram otimizados utilizando as melhores práticas de mapeamento JPA/Hibernate conforme os seguintes artigos:
- [One-to-One Relationship](https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/)
- [One-to-Many Association](https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/)

### Distribuição de Responsabilidades

Para manter a lógica do sistema organizada e modular, removemos as lógicas das controllers e as movemos para classes de service. Isso segue o princípio de responsabilidade única, tornando o código mais fácil de manter e testar.

### Data Transfer Objects (DTOs)

Foram criados DTOs para cada entidade. DTOs são importantes pois:
- Isolam a camada de persistência da camada de apresentação.
- Reduzem o acoplamento entre essas camadas.
- Facilitam a validação e transformação dos dados antes de enviá-los para a UI.

### Requisições de API com Bruno

Utilizamos o [Bruno](https://www.usebruno.com/) para realizar requisições de APIs de forma eficiente e simplificada.

### Boas Práticas para Injeção de Dependências

Seguindo as recomendações do artigo de [Jefferson Fabricio](https://medium.com/@jeffersonfabriciodev/o-uso-do-autowired-no-spring-%C3%A9-uma-m%C3%A1-pratica-a23378be3c27), evitamos o uso de `@Autowired`, preferindo a injeção de dependências através de construtores.

### Commits Semânticos

Adotamos commits semânticos para melhorar a clareza e o histórico do projeto, seguindo o guia:
- [Commits Semânticos](https://blog.geekhunter.com.br/o-que-e-commit-e-como-usar-commits-semanticos/)

### Tratamento de Exceções

Implementamos tratativas de exceções para lidar com erros de forma robusta e fornecer feedback adequado aos usuários.

### Named Queries

Utilizamos Named Queries no repository para melhorar a performance do sistema, garantindo consultas mais eficientes e reutilizáveis.

### Flyway Migration

Usamos Flyway para gerenciar as migrações do banco de dados, garantindo que o esquema esteja sempre atualizado e em conformidade com as versões do aplicativo. Também utilizamos snapshots para manter o controle das versões.

### Docker Compose

Implementamos o `docker-compose` para subir o banco de dados MySQL em um contêiner. Para iniciar o banco de dados, utilize o comando:
```bash
docker-compose up -d db
