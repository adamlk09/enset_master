import React, { useContext, useEffect, useState } from 'react';
import { ThemeContext } from '../App';
import { LanguageContext } from '../context/Languages';
import { useDebounce } from '../hooks/useProductSearch'


const ProductSearch = ({ searchTerm, setSearchTerm }) => {
  const { isDarkTheme } = useContext(ThemeContext);

  const { language } = useContext(LanguageContext);
  const placeholderText = {
    fr: "Rechercher un produit...",
    en: "Search for a product...",
    se: "Buscar un producto...",
  };
  const [inpVal, setInpVal] = useState(searchTerm);
  const debouncedValue = useDebounce(inpVal, 300);

  useEffect(() => {
    setSearchTerm(debouncedValue);
  }, [debouncedValue, setSearchTerm]);

  return (
    <div className="mb-4">
      <input
        type="text"
        value={inpVal}
        onChange={(e) => setInpVal(e.target.value)}
        placeholder={placeholderText[language]}
        className={`form-control ${isDarkTheme ? 'bg-dark text-light' : ''}`}
      />
    </div>
  );
};

export default ProductSearch;