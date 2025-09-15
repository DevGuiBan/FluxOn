CREATE TABLE user_specs (
    id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),

    user_id UUID NOT NULL,
    responsibility_id UUID NOT NULL,

    number VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    rg VARCHAR(20) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_method_details VARCHAR(255),
    bank VARCHAR(100),
    agency VARCHAR(20),
    account VARCHAR(20),

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);