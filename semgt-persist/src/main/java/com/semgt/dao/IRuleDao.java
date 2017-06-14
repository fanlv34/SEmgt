package com.semgt.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.semgt.model.Rule;
import com.semgt.model.RuleKey;

@Repository("ruleDao")
public interface IRuleDao {
	List<Rule> selectAll();
}