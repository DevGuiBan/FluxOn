-- User Specs -> Users (One-to-One)
ALTER TABLE user_specs
ADD CONSTRAINT fk_user_specs_user
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
-- User Specs -> Responsibilities (Many-to-One)
ALTER TABLE user_specs
ADD CONSTRAINT fk_user_specs_responsibility
FOREIGN KEY (responsibility_id) REFERENCES responsibilities(id) ON DELETE SET NULL;
-- Users -> User Specs (One-to-One)
ALTER TABLE users
ADD CONSTRAINT fk_users_user_spec
FOREIGN KEY (user_spec_id) REFERENCES user_specs(id) ON DELETE SET NULL;
