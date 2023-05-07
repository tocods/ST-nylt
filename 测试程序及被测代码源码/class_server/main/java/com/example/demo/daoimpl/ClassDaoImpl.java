package com.example.demo.daoimpl;

import com.example.demo.dao.ClassDao;
import com.example.demo.entity.*;
import com.example.demo.entity.Class;
import com.example.demo.repository.ClassDetailRepository;
import com.example.demo.repository.ClassRepository;
import com.example.demo.util.PageUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassDaoImpl implements ClassDao {
    private int STEP = PageUtils.pageSize;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ClassDetailRepository classDetailRepository;

    /*private List<Integer> getIdList(List<Integer> idList, int index, int pageSize)
    {
        List<Integer> idListTmp = idList;
        List<Integer> tmp = new ArrayList<>();
        if(index * pageSize < idListTmp.size())
        {
            for(int i = (index - 1) * pageSize; i < index * pageSize; i++)
            {
                tmp.add(idListTmp.get(i));
            }
            idListTmp.removeAll(tmp);
        }
        Boolean hasNext = true;
        Random random = new Random(System.currentTimeMillis());
        while (idListTmp.size() > 0)
        {
            int indexTmp  = random.nextInt(idListTmp.size());
            int id = idListTmp.get(indexTmp);
            tmp.add(id);
            idListTmp.remove(indexTmp);
        }
        return tmp;
    }*/

    @Override
    public List<Class> getAllClasses()
    {
        return classRepository.getAllClasses();
    }

    @Override
    public List<Class> getClassesByTag(String tag){return classRepository.getClassesByTag(tag);}

    @Override
    public List<Class> getClassesByTitle(String title){return classRepository.getClassByTitle(title);}

    @Override
    public List<Show> getClassByPage(int pid)
    {

        return classRepository.getClassByPage(pid,pid+STEP-1);
    }

    @Override
    public Class getClassById(int classId, int from)
    {
        Class choose = classRepository.getClassById(classId);
        if(from == 0)
            return choose;
        int time = choose.getClassDetail().getViewCounts();
        choose.getClassDetail().setViewCounts(time+1);
        classDetailRepository.save(choose.getClassDetail());
        return choose;
    }

    @Override
    public List<Integer> getClassIdByTag(String tag)
    {
        return classRepository.getClassIdByTag(tag);
    }

    @Override
    public List<Show> getClassFromIds(List<Integer> ids)
    {
        return classRepository.getClassFromIds(ids);
    }

    @Override
    public  List<Show> getShowByMulti(String tag, String difficulty, String method, String target)
    {
        return classRepository.getShowsFromMulti(tag, difficulty, method, target);
    }



    @Override
    public Uris getUris(int classId)
    {
        return classRepository.getUris(classId);
    }



}
