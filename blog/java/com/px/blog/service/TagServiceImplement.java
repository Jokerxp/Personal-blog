package com.px.blog.service;

import com.px.blog.NotFoundException;
import com.px.blog.dao.TagRepository;
import com.px.blog.po.Blog;
import com.px.blog.po.Tag;
import com.px.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import java.util.Random;

@Service
public class TagServiceImplement implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        String[] colorSet = {"red","blue","purple","teal","orange","pink","brown","black","green"};
        Random random = new Random();
        tag.setColor(colorSet[random.nextInt(colorSet.length)]);
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getById(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    public boolean isDigit(String str){
        Pattern pattern = Pattern.compile("^[0-9]*");
        return pattern.matcher(str).matches();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for(int i=0;i<idarray.length;i++){
                if(!isDigit(idarray[i])){
                    Tag tag = new Tag();
                    tag.setName(idarray[i]);
                    tagRepository.save(tag);
                    list.add(tagRepository.findByName(idarray[i]).getId());
                }else{
                    list.add(Long.valueOf(idarray[i]));
                }
            }
        }
        return list;
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }


    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getById(id);
        if (t == null) {
            throw new NotFoundException("Id not found");
        }
        BeanUtils.copyProperties(tag, t);

        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
