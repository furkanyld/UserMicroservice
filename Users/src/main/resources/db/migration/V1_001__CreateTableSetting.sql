CREATE TABLE users_application.settings (
	
    id BIGSERIAL PRIMARY KEY,  -- Auto-incrementing primary key
    key VARCHAR(255) NOT NULL,  -- Ayar ismi
    value VARCHAR(255) NOT NULL  -- Ayar değeri
   
);


