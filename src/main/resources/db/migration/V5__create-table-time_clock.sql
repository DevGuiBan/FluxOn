CREATE TABLE time_clock (
    id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    user_id UUID NOT NULL,
    clock_in TIME,
    clock_out TIME,
    date DATE NOT NULL,
    attendance_status VARCHAR(50) NOT NULL,
    justification VARCHAR(255),

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);