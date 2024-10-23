package tr.edu.ogu.ceng.Users.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.Users.entity.Users;
import tr.edu.ogu.ceng.Users.repository.UsersRepository;
@RequiredArgsConstructor
@Service
public class UsersService {
 
	private final UsersRepository usersrepository;
	
	public Users createUser(Users user) {
        return usersrepository.save(user); // Yeni kullanıcıyı kaydet
    }

    public Users updateUser(Users user) {
        return usersrepository.save(user); // Var olan kullanıcıyı güncelle
    }


}