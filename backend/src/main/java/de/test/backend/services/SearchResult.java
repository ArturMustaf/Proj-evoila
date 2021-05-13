package de.test.backend.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
/**
 * Helper class for elastic-search searches
 */
public class SearchResult<T> {
    public List<T> data;
    public int resultSize;

}
