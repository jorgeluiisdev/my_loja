## Como rodar o projeto:

### Se você está no macos ou linux, use o docker engine com o seguinte comando para o docker compose:


```` bash
DB_POSTGRES="my_vitrine" DB_USERNAME="postgres" DB_PASSWORD="sua_senha_aqui" docker compose up --build
````

### Caso esteja no windows, seu comando deverá ser assim:

#### E tenha certeza de estar usando o powershell `(recomendado)` para executar.

```` shell
$env:DB_POSTGRES="my_vitrine" $env:DB_USERNAME="postgres" $env:DB_PASSWORD="sua_senha_aqui" docker compose up --build
````