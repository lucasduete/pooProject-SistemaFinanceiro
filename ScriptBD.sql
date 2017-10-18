CREATE TABLE Usuario (
	ID SERIAL,
	Email VARCHAR(100) NOT NULL UNIQUE,
	Password VARCHAR(32) NOT NULL,
	Nome VARCHAR(100) NOT NULL,
	DataNasc DATE,
	Sexo VARCHAR(9),
	CONSTRAINT PK_Usuario_ID PRIMARY KEY(ID),
	CONSTRAINT Email_Usuario_Valido CHECK (Email LIKE ('%@%')),
	CONSTRAINT DataNasc_Usuario_AnteriorAgora CHECK (DataNasc < NOW()),
	CONSTRAINT Sexo_Usuario_MascOUFemin CHECK (Sexo LIKE 'Masculino' OR Sexo LIKE 'Feminino')
);

CREATE TABLE Movimentacao_Financeira (
	ID SERIAL,
	Descricao VARCHAR DEFAULT 'Descrição Não Cadastrada',
	Data DATE DEFAULT NOW(),
	Valor NUMERIC(12,2) NOT NULL DEFAULT 0,
	Tipo VARCHAR(7) NOT NULL,
	Categoria VARCHAR(27) NOT NULL,
	IDUsuario INTEGER NOT NULL,
	CONSTRAINT PK_MOvimentacaoFinanceira_ID PRIMARY KEY(ID),
	CONSTRAINT Data_MovimentacaoFinanceira_Valida CHECK (Data <= NOW()),
	CONSTRAINT Valor_MovimentacaoFinanceira_MaiorIgualZero CHECK (Valor >= 0),
	CONSTRAINT Tipo_MovimentacaoFinanceira_EntraOUSaida CHECK (Tipo LIKE 'Entrada' OR Tipo LIKE 'Saída'),
	CONSTRAINT Categoria_MovimentacaoFinanceira_Valida CHECK (
		Categoria LIKE 'Alimentação' OR 
		Categoria LIKE 'Cartão de Crédito' OR 
		Categoria LIKE 'Despesa Doméstica - Aluguel' OR 
		Categoria LIKE 'Despesa Doméstica - Água' OR 
		Categoria LIKE 'Despesa Doméstica - Luz' OR 
		Categoria LIKE 'Despesa Doméstica - Intenet' OR 
		Categoria LIKE 'Saúde' OR 
		Categoria LIKE 'Pessoal' OR 
		Categoria LIKE 'Outros'
	),
	CONSTRAINT PK_MovimentacaoFinanceira_Usuario_ID FOREIGN KEY(IDUsuario) REFERENCES Usuario(ID)
);