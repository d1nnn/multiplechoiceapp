package com.multiplechoice.Database.Repositories;

import com.multiplechoice.Database.Interfaces.IRepository;

import java.lang.reflect.Constructor;

public class RepositoriesFactory {
    public static <TRepository extends IRepository> TRepository createRepositoryInstance(Class<TRepository> repoClass) {
        try {
            Constructor<TRepository> ctor = repoClass.getConstructor();
            return ctor.newInstance();
        } catch(Exception e) {
            throw new RuntimeException("Failed to create an instance of TRepository");
        }
    }
}
