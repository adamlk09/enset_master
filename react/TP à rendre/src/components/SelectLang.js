import React, { useContext } from 'react';
import { LanguageContext } from '../context/Languages';

const LanguageSelector = () => {
  const { language, changeLanguage } = useContext(LanguageContext);

  return (
    <select value={language} onChange={(e) => changeLanguage(e.target.value)}>
      <option value="fr">Français</option>
      <option value="en">English</option>
      <option value="se">Spanish</option>
    </select>
  );
};

export default LanguageSelector;